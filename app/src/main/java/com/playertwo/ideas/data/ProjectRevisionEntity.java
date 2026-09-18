package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "project_revisions", primaryKeys = {"projectId", "revision"},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
        childColumns = "projectId", onDelete = ForeignKey.CASCADE),
    indices = @Index("projectId"))
public class ProjectRevisionEntity {
    @NonNull public final String projectId;
    public final int revision;
    public final String title;
    public final String status;
    public final long updatedAt;

    public ProjectRevisionEntity(@NonNull String projectId, int revision, String title,
                                 String status, long updatedAt) {
        this.projectId = projectId;
        this.revision = revision;
        this.title = title;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
