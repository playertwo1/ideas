package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "roadmap_items", primaryKeys = {"projectId", "itemId"},
        indices = {@Index("projectId")},
        foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
                childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class RoadmapItemEntity {
    @NonNull public final String projectId;
    @NonNull public final String itemId;
    @NonNull public final String phaseId;
    @NonNull public final String title;
    @NonNull public final String objective;
    @NonNull public final String delivery;
    @NonNull public final String verify;
    @NonNull public final String track;
    @NonNull public final String priority;
    @NonNull public final String dependencies;
    public final int orderIndex;
    public final int revision;
    @NonNull public final String status;
    @NonNull public final String sourceRef;
    public final long updatedAt;

    public RoadmapItemEntity(@NonNull String projectId, @NonNull String itemId, @NonNull String phaseId,
                             @NonNull String title, @NonNull String objective, @NonNull String delivery,
                             @NonNull String verify, @NonNull String track, @NonNull String priority,
                             @NonNull String dependencies, int orderIndex, int revision,
                             @NonNull String status, @NonNull String sourceRef, long updatedAt) {
        this.projectId = projectId; this.itemId = itemId; this.phaseId = phaseId; this.title = title;
        this.objective = objective; this.delivery = delivery; this.verify = verify; this.track = track;
        this.priority = priority; this.dependencies = dependencies; this.orderIndex = orderIndex;
        this.revision = revision; this.status = status; this.sourceRef = sourceRef; this.updatedAt = updatedAt;
    }
}
