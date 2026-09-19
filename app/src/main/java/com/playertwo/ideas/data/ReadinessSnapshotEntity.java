package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "roadmap_readiness", primaryKeys = {"projectId", "snapshotId"},
        indices = {@Index("projectId")},
        foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
                childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class ReadinessSnapshotEntity {
    @NonNull public final String projectId;
    @NonNull public final String snapshotId;
    @NonNull public final String overallStatus;
    @NonNull public final String pendingDimensions;
    @NonNull public final String actionablePending;
    public final long createdAt;

    public ReadinessSnapshotEntity(@NonNull String projectId, @NonNull String snapshotId,
                                   @NonNull String overallStatus, @NonNull String pendingDimensions,
                                   @NonNull String actionablePending, long createdAt) {
        this.projectId = projectId; this.snapshotId = snapshotId; this.overallStatus = overallStatus;
        this.pendingDimensions = pendingDimensions; this.actionablePending = actionablePending; this.createdAt = createdAt;
    }
}
