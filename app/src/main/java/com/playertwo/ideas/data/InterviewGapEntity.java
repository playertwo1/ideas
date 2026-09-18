package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "interview_gaps", primaryKeys = {"projectId", "gapId"},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId", childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class InterviewGapEntity {
    @NonNull public final String projectId;
    @NonNull public final String gapId;
    @NonNull public final String criticality;
    @NonNull public final String prompt;
    @NonNull public final String options;
    @NonNull public final String recommendation;
    @NonNull public final String tradeoffs;
    public final String defaultValue;
    @NonNull public final String status;
    public final String answer;
    public final String answerSource;
    public final long updatedAt;

    public InterviewGapEntity(String projectId, String gapId, String criticality, String prompt, String options,
                              String recommendation, String tradeoffs, String defaultValue, String status,
                              String answer, String answerSource, long updatedAt) {
        this.projectId = projectId; this.gapId = gapId; this.criticality = criticality; this.prompt = prompt;
        this.options = options; this.recommendation = recommendation; this.tradeoffs = tradeoffs;
        this.defaultValue = defaultValue; this.status = status; this.answer = answer;
        this.answerSource = answerSource; this.updatedAt = updatedAt;
    }
}
