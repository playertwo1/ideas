package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "hypotheses",
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId", childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class HypothesisEntity {
    @NonNull @PrimaryKey public final String projectId;
    @NonNull public final String problem;
    @NonNull public final String audience;
    @NonNull public final String statement;
    @NonNull public final String minimumTest;
    @NonNull public final String metric;
    @NonNull public final String threshold;
    @NonNull public final String evidenceStatus;
    @NonNull public final String status;
    public final long updatedAt;

    public HypothesisEntity(@NonNull String projectId, @NonNull String problem, @NonNull String audience,
                            @NonNull String statement, @NonNull String minimumTest, @NonNull String metric,
                            @NonNull String threshold, @NonNull String evidenceStatus, @NonNull String status,
                            long updatedAt) {
        this.projectId = projectId;
        this.problem = problem;
        this.audience = audience;
        this.statement = statement;
        this.minimumTest = minimumTest;
        this.metric = metric;
        this.threshold = threshold;
        this.evidenceStatus = evidenceStatus;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
