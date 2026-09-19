package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "planning_batches", primaryKeys = {"projectId", "batchId"},
    indices = {@Index("projectId")},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
        childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class PlanningBatchEntity {
    @NonNull public final String projectId;
    @NonNull public final String batchId;
    @NonNull public final String status;
    @NonNull public final String author;
    public final long createdAt;

    public PlanningBatchEntity(@NonNull String projectId, @NonNull String batchId, @NonNull String status,
                               @NonNull String author, long createdAt) {
        this.projectId = projectId;
        this.batchId = batchId;
        this.status = status;
        this.author = author;
        this.createdAt = createdAt;
    }
}
