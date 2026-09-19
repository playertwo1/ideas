package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "roadmap_phases", primaryKeys = {"projectId", "phaseId"},
        indices = {@Index("projectId")},
        foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
                childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class PhaseEntity {
    @NonNull public final String projectId;
    @NonNull public final String phaseId;
    @NonNull public final String title;
    @NonNull public final String objective;
    @NonNull public final String track;
    public final int priority;
    public final int revision;
    @NonNull public final String status;
    @NonNull public final String sourceRef;
    public final long updatedAt;

    public PhaseEntity(@NonNull String projectId, @NonNull String phaseId, @NonNull String title,
                       @NonNull String objective, @NonNull String track, int priority, int revision,
                       @NonNull String status, @NonNull String sourceRef, long updatedAt) {
        this.projectId = projectId; this.phaseId = phaseId; this.title = title;
        this.objective = objective; this.track = track; this.priority = priority;
        this.revision = revision; this.status = status; this.sourceRef = sourceRef; this.updatedAt = updatedAt;
    }
}
