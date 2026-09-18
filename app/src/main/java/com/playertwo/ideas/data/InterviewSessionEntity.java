package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "interview_sessions",
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId", childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class InterviewSessionEntity {
    @NonNull @PrimaryKey public final String projectId;
    @NonNull public final String projectType;
    @NonNull public final String depthMode;
    @NonNull public final String status;
    public final int round;
    public final int maxRounds;
    public final boolean gateBlocked;
    @NonNull public final String nextAction;
    public final long updatedAt;

    public InterviewSessionEntity(String projectId, String projectType, String depthMode, String status,
                                  int round, int maxRounds, boolean gateBlocked, String nextAction, long updatedAt) {
        this.projectId = projectId; this.projectType = projectType; this.depthMode = depthMode;
        this.status = status; this.round = round; this.maxRounds = maxRounds; this.gateBlocked = gateBlocked;
        this.nextAction = nextAction; this.updatedAt = updatedAt;
    }
}
