package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "decision_history", indices = {@Index("projectId")},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId", childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class DecisionHistoryEntity {
    @PrimaryKey(autoGenerate = true) public long historyId;
    @NonNull public final String projectId;
    public final int revision;
    @NonNull public final String action;
    @NonNull public final String actor;
    public final String gapId;
    @NonNull public final String detail;
    public final long createdAt;

    public DecisionHistoryEntity(String projectId, int revision, String action, String actor, String gapId,
                                 String detail, long createdAt) {
        this.projectId = projectId; this.revision = revision; this.action = action; this.actor = actor;
        this.gapId = gapId; this.detail = detail; this.createdAt = createdAt;
    }
}
