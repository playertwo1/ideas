package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_intake",
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
        childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class ProjectIntakeEntity {
    @NonNull @PrimaryKey public final String projectId;
    @NonNull public final String originalTitle;
    @NonNull public final String originalIdea;
    @NonNull public final String originalLimits;
    @NonNull public final String originalHash;
    public final String interpretation;
    public final String projectType;
    public final String restrictions;
    public final String depthMode;
    public final String suggestionReason;
    public final boolean interpretationAccepted;
    public final int progressPercent;
    @NonNull public final String nextAction;
    public final long updatedAt;

    public ProjectIntakeEntity(@NonNull String projectId, @NonNull String originalTitle, @NonNull String originalIdea,
                               @NonNull String originalLimits, @NonNull String originalHash, String interpretation,
                               String projectType, String restrictions, String depthMode, String suggestionReason,
                               boolean interpretationAccepted, int progressPercent, @NonNull String nextAction,
                               long updatedAt) {
        this.projectId = projectId; this.originalTitle = originalTitle; this.originalIdea = originalIdea;
        this.originalLimits = originalLimits; this.originalHash = originalHash; this.interpretation = interpretation;
        this.projectType = projectType; this.restrictions = restrictions; this.depthMode = depthMode;
        this.suggestionReason = suggestionReason; this.interpretationAccepted = interpretationAccepted;
        this.progressPercent = progressPercent; this.nextAction = nextAction; this.updatedAt = updatedAt;
    }
}
