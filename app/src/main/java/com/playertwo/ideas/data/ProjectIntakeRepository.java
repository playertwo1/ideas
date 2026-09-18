package com.playertwo.ideas.data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** F05 local intake workflow; original fields are write-once after creation. */
public final class ProjectIntakeRepository {
    private final IdeaDatabase database;

    public ProjectIntakeRepository(IdeaDatabase database) {
        if (database == null) throw new IllegalArgumentException("database required");
        this.database = database;
    }

    public ProjectIntakeEntity create(String projectId, String title, String idea, String limits) {
        required(projectId, "projectId"); required(title, "title"); required(idea, "idea");
        String safeLimits = limits == null ? "" : limits;
        long now = System.currentTimeMillis();
        int initialProgress = idea.trim().isEmpty() ? 0 : 40;
        String initialAction = idea.trim().isEmpty() ? "Informar a ideia" : "Gerar interpretação";
        ProjectIntakeEntity intake = new ProjectIntakeEntity(projectId, title, idea, safeLimits,
            originalHash(title, idea, safeLimits), null, null, null, null, null, false, initialProgress, initialAction, now);
        database.runInTransaction(() -> {
            if (database.projects().find(projectId) != null || database.intakes().find(projectId) != null)
                throw new IllegalStateException("project already exists");
            database.projects().insert(new ProjectEntity(projectId, title, "ACTIVE", now));
            database.intakes().insert(intake);
            database.drafts().save(new DraftEntity(projectId, idea, now));
        });
        return intake;
    }

    /** Creates the first snapshot for an old placeholder project, without accepting an empty idea. */
    public ProjectIntakeEntity createForLegacy(String projectId, String title, String idea, String limits) {
        required(projectId, "projectId"); required(title, "title"); required(idea, "idea");
        String safeLimits = limits == null ? "" : limits;
        long now = System.currentTimeMillis();
        int initialProgress = 40;
        ProjectIntakeEntity intake = new ProjectIntakeEntity(projectId, title, idea, safeLimits,
            originalHash(title, idea, safeLimits), null, null, null, null, null, false, initialProgress, "Gerar interpretação", now);
        database.runInTransaction(() -> {
            if (database.projects().find(projectId) == null || database.intakes().find(projectId) != null)
                throw new IllegalStateException("legacy project unavailable");
            database.intakes().insert(intake);
            database.drafts().save(new DraftEntity(projectId, idea, now));
        });
        return intake;
    }

    public ProjectIntakeEntity find(String projectId) { return database.intakes().find(required(projectId, "projectId")); }

    /** Bridges the legacy F03 home screen without changing the original snapshot. */
    public void ensureFromLegacy(String projectId) {
        String id = required(projectId, "projectId");
        database.runInTransaction(() -> {
            if (database.intakes().find(id) != null) return;
            ProjectEntity project = database.projects().find(id);
            if (project == null) throw new IllegalArgumentException("unknown projectId");
            DraftEntity draft = database.drafts().find(id);
            String idea = draft == null || draft.content == null ? "" : draft.content;
            if (idea.trim().isEmpty()) return;
            database.intakes().insert(new ProjectIntakeEntity(id, project.title, idea, "",
                originalHash(project.title, idea, ""), null, null, null, null, null, false, 40,
                "Gerar interpretação", System.currentTimeMillis()));
        });
    }

    public void updateSuggestion(String projectId, String interpretation, String projectType,
                                 String restrictions, String depthMode, String reason) {
        required(projectId, "projectId"); required(interpretation, "interpretation");
        required(projectType, "projectType"); required(depthMode, "depthMode"); required(reason, "reason");
        database.runInTransaction(() -> {
            if (database.intakes().updateSuggestion(projectId, interpretation, projectType,
                restrictions == null ? "" : restrictions, depthMode, reason, System.currentTimeMillis()) == 0)
                throw new IllegalArgumentException("unknown projectId");
            refreshProgressInTransaction(projectId);
        });
    }

    public void acceptSuggestion(String projectId) {
        String id = required(projectId, "projectId");
        database.runInTransaction(() -> {
            if (database.intakes().acceptSuggestion(id, System.currentTimeMillis()) == 0)
                throw new IllegalStateException("interpretation required");
            refreshProgressInTransaction(id);
        });
    }

    public void rejectSuggestion(String projectId) {
        String id = required(projectId, "projectId");
        database.runInTransaction(() -> {
            if (database.intakes().rejectSuggestion(id, System.currentTimeMillis()) == 0)
                throw new IllegalArgumentException("unknown projectId");
            refreshProgressInTransaction(id);
        });
    }

    /** Recomputes the explainable local progress from persisted intake fields only. */
    public void refreshProgress(String projectId) {
        database.runInTransaction(() -> refreshProgressInTransaction(required(projectId, "projectId")));
    }

    private void refreshProgressInTransaction(String projectId) {
        ProjectIntakeEntity value = database.intakes().find(projectId);
        if (value == null) throw new IllegalArgumentException("unknown projectId");
        int progress;
        String action;
        if (value.originalIdea.trim().isEmpty()) { progress = 0; action = "Informar a ideia"; }
        else if (value.interpretation == null) { progress = 40; action = "Gerar interpretação"; }
        else if (value.interpretationAccepted) { progress = 80; action = "Aguardando próxima fase"; }
        else { progress = 60; action = "Aceitar ou rejeitar sugestão"; }
        database.intakes().updateProgress(projectId, progress, action, System.currentTimeMillis());
    }

    public void updateProgress(String projectId, int progress, String nextAction) {
        required(projectId, "projectId"); required(nextAction, "nextAction");
        if (progress < 0 || progress > 100) throw new IllegalArgumentException("progress out of range");
        if (database.intakes().updateProgress(projectId, progress, nextAction, System.currentTimeMillis()) == 0)
            throw new IllegalArgumentException("unknown projectId");
    }

    public static String originalHash(String title, String idea, String limits) {
        try {
            byte[] bytes = (title + "\n" + idea + "\n" + limits).getBytes(StandardCharsets.UTF_8);
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(bytes);
            StringBuilder result = new StringBuilder();
            for (byte item : digest) result.append(String.format("%02x", item & 0xff));
            return result.toString();
        } catch (NoSuchAlgorithmException error) { throw new AssertionError(error); }
    }

    private static String required(String value, String name) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(name + " required");
        return value;
    }
}
