package com.playertwo.ideas.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "projects")
public class ProjectEntity {
    @NonNull @PrimaryKey public final String projectId;
    public final String title;
    public final String status;
    public final long updatedAt;
    public ProjectEntity(String projectId, String title, String status, long updatedAt) {
        this.projectId = projectId; this.title = title; this.status = status; this.updatedAt = updatedAt;
    }
}
