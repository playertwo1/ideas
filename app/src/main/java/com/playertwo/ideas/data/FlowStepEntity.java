package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "flow_steps", primaryKeys = {"projectId", "stepOrder"},
    indices = {@Index("projectId")},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
        childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class FlowStepEntity {
    @NonNull public final String projectId;
    public final int stepOrder;
    @NonNull public final String stateName;
    @NonNull public final String action;
    @NonNull public final String expectedResult;
    public final String failureCondition;
    public final String failureHandling;
    @NonNull public final String status;
    public final long updatedAt;

    public FlowStepEntity(@NonNull String projectId, int stepOrder, @NonNull String stateName,
                          @NonNull String action, @NonNull String expectedResult, String failureCondition,
                          String failureHandling, @NonNull String status, long updatedAt) {
        this.projectId = projectId;
        this.stepOrder = stepOrder;
        this.stateName = stateName;
        this.action = action;
        this.expectedResult = expectedResult;
        this.failureCondition = failureCondition;
        this.failureHandling = failureHandling;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
