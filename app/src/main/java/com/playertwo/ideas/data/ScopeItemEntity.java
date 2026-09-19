package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "scope_items", primaryKeys = {"projectId", "itemId"},
    indices = {@Index("projectId")},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
        childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class ScopeItemEntity {
    @NonNull public final String projectId;
    @NonNull public final String itemId;
    @NonNull public final String priority;
    @NonNull public final String title;
    @NonNull public final String rationale;
    @NonNull public final String dependencies;
    public final boolean isNonGoal;
    @NonNull public final String status;
    public final long updatedAt;

    public ScopeItemEntity(@NonNull String projectId, @NonNull String itemId, @NonNull String priority,
                           @NonNull String title, @NonNull String rationale, @NonNull String dependencies,
                           boolean isNonGoal, @NonNull String status, long updatedAt) {
        this.projectId = projectId;
        this.itemId = itemId;
        this.priority = priority;
        this.title = title;
        this.rationale = rationale;
        this.dependencies = dependencies;
        this.isNonGoal = isNonGoal;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
