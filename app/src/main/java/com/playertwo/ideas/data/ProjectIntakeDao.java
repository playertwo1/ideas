package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ProjectIntakeDao {
    @Insert(onConflict = OnConflictStrategy.ABORT) void insert(ProjectIntakeEntity intake);
    @Query("SELECT * FROM project_intake WHERE projectId = :id") ProjectIntakeEntity find(String id);
    @Query("UPDATE project_intake SET interpretation = :interpretation, projectType = :projectType, restrictions = :restrictions, depthMode = :depthMode, suggestionReason = :reason, interpretationAccepted = 0, updatedAt = :updatedAt WHERE projectId = :id")
    int updateSuggestion(String id, String interpretation, String projectType, String restrictions, String depthMode, String reason, long updatedAt);
    @Query("UPDATE project_intake SET interpretationAccepted = 1, updatedAt = :updatedAt WHERE projectId = :id AND interpretation IS NOT NULL")
    int acceptSuggestion(String id, long updatedAt);
    @Query("UPDATE project_intake SET interpretation = NULL, projectType = NULL, restrictions = NULL, depthMode = NULL, suggestionReason = NULL, interpretationAccepted = 0, updatedAt = :updatedAt WHERE projectId = :id")
    int rejectSuggestion(String id, long updatedAt);
    @Query("UPDATE project_intake SET progressPercent = :progress, nextAction = :nextAction, updatedAt = :updatedAt WHERE projectId = :id")
    int updateProgress(String id, int progress, String nextAction, long updatedAt);
}
