package com.playertwo.ideas.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.ForeignKey;

@Entity(tableName = "drafts", foreignKeys = @ForeignKey(entity = ProjectEntity.class,
    parentColumns = "projectId", childColumns = "projectId", onDelete = ForeignKey.CASCADE))
public class DraftEntity {
    @NonNull @PrimaryKey public final String projectId;
    public final String content;
    public final long updatedAt;
    public DraftEntity(String projectId, String content, long updatedAt) {
        this.projectId = projectId; this.content = content; this.updatedAt = updatedAt;
    }
}
