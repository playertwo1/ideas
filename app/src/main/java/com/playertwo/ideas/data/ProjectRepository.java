package com.playertwo.ideas.data;

import androidx.room.Transaction;

public final class ProjectRepository {
    private final IdeaDatabase database;
    public ProjectRepository(IdeaDatabase database) { this.database = database; }

    @Transaction
    public void saveRevision(ProjectEntity project, ProjectEventEntity event) {
        database.runInTransaction(() -> {
            ProjectEntity current = database.projects().find(project.projectId);
            if (current == null) database.projects().insert(project);
            else database.projects().updateStatus(project.projectId, project.status, project.updatedAt);
            database.events().insert(event);
        });
    }

    public void archive(String projectId) { database.projects().updateStatus(projectId, "ARCHIVED", System.currentTimeMillis()); }
    public void restore(String projectId) { database.projects().updateStatus(projectId, "ACTIVE", System.currentTimeMillis()); }
    public void deletePermanently(String projectId) { database.projects().delete(projectId); }
}
