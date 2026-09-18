package com.playertwo.ideas.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "project_events", foreignKeys = @ForeignKey(entity = ProjectEntity.class,
    parentColumns = "projectId", childColumns = "projectId", onDelete = ForeignKey.CASCADE),
    indices = @Index("projectId"))
public class ProjectEventEntity {
    @PrimaryKey(autoGenerate = true) public long eventId;
    @NonNull public final String projectId;
    public final String type;
    public final long createdAt;
    public ProjectEventEntity(String projectId, String type, long createdAt) {
        this.projectId = projectId; this.type = type; this.createdAt = createdAt;
    }
}
