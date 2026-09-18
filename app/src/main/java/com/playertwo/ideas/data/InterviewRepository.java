package com.playertwo.ideas.data;

import java.util.ArrayList;
import java.util.List;

/** Offline interview state machine with explicit human decision transitions. */
public final class InterviewRepository {
    private final IdeaDatabase database;

    public InterviewRepository(IdeaDatabase database) {
        if (database == null) throw new IllegalArgumentException("database required");
        this.database = database;
    }

    public InterviewSessionEntity initialize(String projectId, String projectType, String depthMode, int maxRounds) {
        required(projectId, "projectId"); required(projectType, "projectType"); required(depthMode, "depthMode");
        if (maxRounds <= 0) throw new IllegalArgumentException("maxRounds must be positive");
        List<GapDefinition> catalog = GapCatalog.forTypeAndMode(projectType, depthMode);
        long now = System.currentTimeMillis();
        InterviewSessionEntity session = new InterviewSessionEntity(projectId, projectType, depthMode, "ACTIVE", 1,
            maxRounds, hasCritical(catalog), hasCritical(catalog) ? "Responder lacunas CRÍTICAS" : "Refinar proposta", now);
        database.runInTransaction(() -> {
            if (database.projects().find(projectId) == null) throw new IllegalArgumentException("unknown projectId");
            if (database.interviewSessions().find(projectId) != null) throw new IllegalStateException("interview already exists");
            database.interviewSessions().insert(session);
            ArrayList<InterviewGapEntity> gaps = new ArrayList<>();
            for (GapDefinition gap : catalog) gaps.add(new InterviewGapEntity(projectId, gap.gapId, gap.criticality,
                gap.prompt, gap.options, gap.recommendation, gap.tradeoffs, gap.defaultValue, "OPEN", null, null, now));
            database.interviewGaps().insertAll(gaps);
            database.decisionRevisions().insert(new DecisionRevisionEntity(projectId, 1, "PROPOSED", "SYSTEM", "Entrevista iniciada", true, now));
            history(projectId, 1, "START", "SYSTEM", null, "Catálogo carregado", now);
        });
        return session;
    }

    public InterviewSessionEntity session(String projectId) { return database.interviewSessions().find(required(projectId, "projectId")); }
    public List<InterviewGapEntity> nextQuestions(String projectId) { return database.interviewGaps().open(required(projectId, "projectId")); }
    public List<InterviewGapEntity> allQuestions(String projectId) { return database.interviewGaps().all(required(projectId, "projectId")); }
    public List<DecisionHistoryEntity> history(String projectId) { return database.decisionHistory().forProject(required(projectId, "projectId")); }
    public DecisionRevisionEntity latestRevision(String projectId) { return database.decisionRevisions().latest(required(projectId, "projectId")); }

    public void answer(String projectId, String gapId, String answer, String actor) {
        changeAnswer(projectId, gapId, answer, actor, "ANSWER");
    }

    public void editAnswer(String projectId, String gapId, String answer, String actor) {
        changeAnswer(projectId, gapId, answer, actor, "EDIT");
    }

    public void acceptDefault(String projectId, String gapId, String actor) {
        String id = required(projectId, "projectId"); humanOrSystem(actor);
        database.runInTransaction(() -> {
            InterviewGapEntity gap = gap(id, gapId);
            if (gap.defaultValue == null || gap.defaultValue.trim().isEmpty()) throw new IllegalStateException("default unavailable");
            if (database.interviewGaps().answer(id, gap.gapId, "ANSWERED", gap.defaultValue, "DEFAULT", System.currentTimeMillis()) == 0)
                throw new IllegalArgumentException("gap unavailable");
            history(id, revision(id), "DEFAULT_ACCEPTED", actor, gap.gapId, gap.defaultValue, System.currentTimeMillis());
            refresh(id);
        });
    }

    public void defer(String projectId, String gapId, String actor) {
        String id = required(projectId, "projectId"); humanOrSystem(actor);
        database.runInTransaction(() -> {
            InterviewGapEntity gap = gap(id, gapId);
            database.interviewGaps().answer(id, gap.gapId, "DEFERRED", null, null, System.currentTimeMillis());
            history(id, revision(id), "DEFER", actor, gap.gapId, "Adiada explicitamente", System.currentTimeMillis());
            refresh(id);
        });
    }

    public void propose(String projectId, String actor, String reason) { transition(projectId, actor, "PROPOSED", "PROPOSE", reason); }
    public void refine(String projectId, String actor, String reason) { transition(projectId, actor, "REFINED", "REFINE", reason); }

    public void lock(String projectId, String actor, String reason) {
        String id = required(projectId, "projectId"); humanOnly(actor); required(reason, "reason");
        database.runInTransaction(() -> {
            InterviewSessionEntity session = requireSession(id);
            if (session.status.equals("EXHAUSTED")) throw new IllegalStateException("interview budget exhausted");
            for (InterviewGapEntity gap : database.interviewGaps().all(id))
                if ("CRITICAL".equals(gap.criticality) && !"ANSWERED".equals(gap.status))
                    throw new IllegalStateException("critical gap blocks lock");
            int next = revision(id) + 1; long now = System.currentTimeMillis();
            database.decisionRevisions().insert(new DecisionRevisionEntity(id, next, "LOCKED", actor, reason, true, now));
            history(id, next, "LOCK", actor, null, reason, now);
            database.interviewSessions().update(id, "LOCKED", session.round, false, "Decisão bloqueada por ação humana", now);
        });
    }

    public void reopen(String projectId, String gapId, String actor, String reason) {
        String id = required(projectId, "projectId"); humanOnly(actor); required(reason, "reason");
        database.runInTransaction(() -> {
            InterviewSessionEntity session = requireSession(id);
            InterviewGapEntity gap = gap(id, gapId);
            database.decisionRevisions().invalidateAll(id);
            database.interviewGaps().answer(id, gap.gapId, "OPEN", null, null, System.currentTimeMillis());
            int next = revision(id) + 1; long now = System.currentTimeMillis();
            database.decisionRevisions().insert(new DecisionRevisionEntity(id, next, "PROPOSED", actor, reason, false, now));
            history(id, next, "REOPEN", actor, gap.gapId, reason, now);
            database.interviewSessions().update(id, "ACTIVE", session.round, true, "Revisar delta reaberto", now);
        });
    }

    public void advanceRound(String projectId) {
        String id = required(projectId, "projectId");
        database.runInTransaction(() -> {
            InterviewSessionEntity session = requireSession(id); long now = System.currentTimeMillis();
            if (session.status.equals("LOCKED")) throw new IllegalStateException("decision already locked");
            if (session.round >= session.maxRounds) {
                database.interviewSessions().update(id, "EXHAUSTED", session.round, true,
                    "Escolha: responder lacunas, reabrir ou concluir manualmente", now);
                history(id, revision(id), "STOP", "SYSTEM", null, "Orçamento de rodadas esgotado", now);
            } else {
                database.interviewSessions().update(id, "ACTIVE", session.round + 1, session.gateBlocked,
                    session.nextAction, now);
                history(id, revision(id), "ROUND", "SYSTEM", null, "Rodada incrementada", now);
            }
        });
    }

    private void changeAnswer(String projectId, String gapId, String answer, String actor, String action) {
        String id = required(projectId, "projectId"); required(answer, "answer"); humanOrSystem(actor);
        database.runInTransaction(() -> {
            InterviewGapEntity gap = gap(id, gapId);
            if (database.interviewGaps().answer(id, gap.gapId, "ANSWERED", answer, "USER", System.currentTimeMillis()) == 0)
                throw new IllegalArgumentException("gap unavailable");
            history(id, revision(id), action, actor, gap.gapId, answer, System.currentTimeMillis());
            refresh(id);
        });
    }

    private void transition(String projectId, String actor, String status, String action, String reason) {
        String id = required(projectId, "projectId"); humanOrSystem(actor); required(reason, "reason");
        database.runInTransaction(() -> {
            InterviewSessionEntity session = requireSession(id);
            if (session.status.equals("LOCKED")) throw new IllegalStateException("decision already locked");
            int next = revision(id) + 1; long now = System.currentTimeMillis();
            database.decisionRevisions().insert(new DecisionRevisionEntity(id, next, status, actor, reason, true, now));
            history(id, next, action, actor, null, reason, now);
        });
    }

    private void refresh(String projectId) {
        InterviewSessionEntity session = requireSession(projectId); boolean blocked = false; boolean open = false;
        for (InterviewGapEntity gap : database.interviewGaps().all(projectId)) {
            if ("CRITICAL".equals(gap.criticality) && !"ANSWERED".equals(gap.status)) blocked = true;
            if ("OPEN".equals(gap.status)) open = true;
        }
        String action = blocked ? "Responder lacunas CRÍTICAS" : open ? "Responder lacunas restantes" : "Refinar proposta";
        database.interviewSessions().update(projectId, "ACTIVE", session.round, blocked, action, System.currentTimeMillis());
    }

    private InterviewGapEntity gap(String projectId, String gapId) {
        InterviewGapEntity value = database.interviewGaps().find(projectId, required(gapId, "gapId"));
        if (value == null) throw new IllegalArgumentException("unknown gap");
        return value;
    }

    private InterviewSessionEntity requireSession(String projectId) {
        InterviewSessionEntity value = database.interviewSessions().find(projectId);
        if (value == null) throw new IllegalArgumentException("unknown interview");
        return value;
    }

    private int revision(String projectId) {
        DecisionRevisionEntity value = database.decisionRevisions().latest(projectId);
        if (value == null) throw new IllegalStateException("missing decision revision");
        return value.revision;
    }

    private void history(String projectId, int revision, String action, String actor, String gapId, String detail, long now) {
        database.decisionHistory().insert(new DecisionHistoryEntity(projectId, revision, action, actor, gapId, detail, now));
    }

    private static boolean hasCritical(List<GapDefinition> gaps) {
        for (GapDefinition gap : gaps) if ("CRITICAL".equals(gap.criticality)) return true;
        return false;
    }

    private static void humanOrSystem(String actor) { required(actor, "actor"); }
    private static void humanOnly(String actor) {
        required(actor, "actor");
        if ("AI".equalsIgnoreCase(actor) || "SYSTEM".equalsIgnoreCase(actor)) throw new IllegalStateException("human action required");
    }
    private static String required(String value, String name) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(name + " required");
        return value;
    }
}
