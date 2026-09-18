package com.playertwo.ideas.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

@Entity(tableName = "projects")
public class ProjectEntity {
    @NonNull @PrimaryKey public final String projectId;
    public final String title;
    public final String status;
    public final long updatedAt;
    @ColumnInfo(defaultValue = "0") public final long archivedAt;
    @androidx.room.Ignore public ProjectEntity(String projectId, String title, String status, long updatedAt) {
        this(projectId, title, status, updatedAt, 0);
    }
    public ProjectEntity(String projectId, String title, String status, long updatedAt, long archivedAt) {
        this.projectId = projectId; this.title = title; this.status = status; this.updatedAt = updatedAt; this.archivedAt = archivedAt;
    }
}
