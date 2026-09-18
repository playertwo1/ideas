package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface InterviewGapDao {
    @Insert(onConflict = OnConflictStrategy.ABORT) void insertAll(List<InterviewGapEntity> gaps);
    @Query("SELECT * FROM interview_gaps WHERE projectId = :projectId AND status = 'OPEN' ORDER BY gapId") List<InterviewGapEntity> open(String projectId);
    @Query("SELECT * FROM interview_gaps WHERE projectId = :projectId ORDER BY gapId") List<InterviewGapEntity> all(String projectId);
    @Query("SELECT * FROM interview_gaps WHERE projectId = :projectId AND gapId = :gapId") InterviewGapEntity find(String projectId, String gapId);
    @Query("UPDATE interview_gaps SET status = :status, answer = :answer, answerSource = :source, updatedAt = :updatedAt WHERE projectId = :projectId AND gapId = :gapId")
    int answer(String projectId, String gapId, String status, String answer, String source, long updatedAt);
}
