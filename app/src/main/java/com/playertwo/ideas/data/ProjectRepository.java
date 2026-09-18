package com.playertwo.ideas.data;

import androidx.room.Transaction;

public final class ProjectRepository {
    private final IdeaDatabase database;
    public ProjectRepository(IdeaDatabase database) { this.database = database; }

    @Transaction
    public void saveRevision(ProjectEntity project, ProjectEventEntity event) {
        database.runInTransaction(() -> {
            if (!project.projectId.equals(event.projectId)) throw new IllegalArgumentException("projectId mismatch");
            ProjectEntity current = database.projects().find(project.projectId);
            if (current == null) database.projects().insert(project);
            else database.projects().updateRevision(project.projectId, project.title, project.status, project.updatedAt);
            database.revisions().insert(new ProjectRevisionEntity(project.projectId,
                database.revisions().latest(project.projectId) + 1, project.title, project.status, project.updatedAt));
            if (event.type == null || event.type.isEmpty()) throw new IllegalArgumentException("event type required");
            database.events().insert(event);
        });
    }

    public void saveDraft(String projectId, String content, long updatedAt) {
        database.runInTransaction(() -> {
            if (database.projects().find(projectId) == null) throw new IllegalArgumentException("unknown projectId");
            database.drafts().save(new DraftEntity(projectId, content, updatedAt));
        });
    }

    public void archive(String projectId) { database.projects().updateStatus(projectId, "ARCHIVED", System.currentTimeMillis()); }
    public void restore(String projectId) { database.projects().updateStatus(projectId, "ACTIVE", System.currentTimeMillis()); }
    public void deletePermanently(String projectId) { database.projects().delete(projectId); }
}
