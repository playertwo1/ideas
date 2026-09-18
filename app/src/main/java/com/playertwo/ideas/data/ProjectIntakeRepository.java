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
        ProjectIntakeEntity intake = new ProjectIntakeEntity(projectId, title, idea, safeLimits,
            originalHash(title, idea, safeLimits), null, null, null, null, null, false, 0, "Iniciar captura", now);
        database.runInTransaction(() -> {
            if (database.projects().find(projectId) != null || database.intakes().find(projectId) != null)
                throw new IllegalStateException("project already exists");
            database.projects().insert(new ProjectEntity(projectId, title, "ACTIVE", now));
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
            database.intakes().insert(new ProjectIntakeEntity(id, project.title, idea, "",
                originalHash(project.title, idea, ""), null, null, null, null, null, false, 0,
                "Iniciar captura", System.currentTimeMillis()));
        });
    }

    public void updateSuggestion(String projectId, String interpretation, String projectType,
                                 String restrictions, String depthMode, String reason) {
        required(projectId, "projectId"); required(interpretation, "interpretation");
        required(projectType, "projectType"); required(depthMode, "depthMode"); required(reason, "reason");
        if (database.intakes().updateSuggestion(projectId, interpretation, projectType,
            restrictions == null ? "" : restrictions, depthMode, reason, System.currentTimeMillis()) == 0)
            throw new IllegalArgumentException("unknown projectId");
    }

    public void acceptSuggestion(String projectId) {
        if (database.intakes().acceptSuggestion(required(projectId, "projectId"), System.currentTimeMillis()) == 0)
            throw new IllegalStateException("interpretation required");
    }

    public void rejectSuggestion(String projectId) {
        if (database.intakes().rejectSuggestion(required(projectId, "projectId"), System.currentTimeMillis()) == 0)
            throw new IllegalArgumentException("unknown projectId");
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
