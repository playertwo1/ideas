package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface InterviewSessionDao {
    @Insert(onConflict = OnConflictStrategy.ABORT) void insert(InterviewSessionEntity session);
    @Query("SELECT * FROM interview_sessions WHERE projectId = :id") InterviewSessionEntity find(String id);
    @Query("UPDATE interview_sessions SET status = :status, round = :round, gateBlocked = :blocked, nextAction = :nextAction, updatedAt = :updatedAt WHERE projectId = :id")
    int update(String id, String status, int round, boolean blocked, String nextAction, long updatedAt);
}
