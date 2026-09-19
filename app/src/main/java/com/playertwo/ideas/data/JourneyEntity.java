package com.playertwo.ideas.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "journeys", primaryKeys = {"projectId", "journeyId"},
    indices = {@Index("projectId")},
    foreignKeys = @ForeignKey(entity = ProjectEntity.class, parentColumns = "projectId",
        childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public final class JourneyEntity {
    @NonNull public final String projectId;
    @NonNull public final String journeyId;
    @NonNull public final String title;
    @NonNull public final String states;
    @NonNull public final String materialErrors;
    public final String linkedReqId;
    @NonNull public final String status;
    public final long updatedAt;

    public JourneyEntity(@NonNull String projectId, @NonNull String journeyId, @NonNull String title,
                         @NonNull String states, @NonNull String materialErrors, String linkedReqId,
                         @NonNull String status, long updatedAt) {
        this.projectId = projectId;
        this.journeyId = journeyId;
        this.title = title;
        this.states = states;
        this.materialErrors = materialErrors;
        this.linkedReqId = linkedReqId;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
