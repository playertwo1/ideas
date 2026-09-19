package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "requirements", primaryKeys = {"projectId", "reqId"},
    indices = {@Index("projectId")},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
        childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class RequirementEntity {
    @NonNull public final String projectId;
    @NonNull public final String reqId;
    @NonNull public final String type;
    @NonNull public final String scopeItemId;
    @NonNull public final String origin;
    @NonNull public final String rationale;
    @NonNull public final String acceptance;
    @NonNull public final String status;
    public final long updatedAt;

    public RequirementEntity(@NonNull String projectId, @NonNull String reqId, @NonNull String type,
                             @NonNull String scopeItemId, @NonNull String origin, @NonNull String rationale,
                             @NonNull String acceptance, @NonNull String status, long updatedAt) {
        this.projectId = projectId;
        this.reqId = reqId;
        this.type = type;
        this.scopeItemId = scopeItemId;
        this.origin = origin;
        this.rationale = rationale;
        this.acceptance = acceptance;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
