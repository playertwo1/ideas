package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "decision_revisions", primaryKeys = {"projectId", "revision"},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId", childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class DecisionRevisionEntity {
    @NonNull public final String projectId;
    public final int revision;
    @NonNull public final String status;
    @NonNull public final String author;
    @NonNull public final String reason;
    public final boolean derivedValid;
    public final long createdAt;

    public DecisionRevisionEntity(String projectId, int revision, String status, String author, String reason,
                                  boolean derivedValid, long createdAt) {
        this.projectId = projectId; this.revision = revision; this.status = status; this.author = author;
        this.reason = reason; this.derivedValid = derivedValid; this.createdAt = createdAt;
    }
}
