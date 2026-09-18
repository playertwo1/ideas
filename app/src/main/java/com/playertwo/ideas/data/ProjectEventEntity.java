package com.playertwo.ideas.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_events")
public class ProjectEventEntity {
    @PrimaryKey(autoGenerate = true) public long eventId;
    public final String projectId;
    public final String type;
    public final long createdAt;
    public ProjectEventEntity(String projectId, String type, long createdAt) {
        this.projectId = projectId; this.type = type; this.createdAt = createdAt;
    }
}
