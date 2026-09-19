package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface HypothesisDao {
    @Query("SELECT * FROM hypotheses WHERE projectId = :projectId")
    HypothesisEntity find(String projectId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(HypothesisEntity entity);

    @Query("UPDATE hypotheses SET status = :status, updatedAt = :updatedAt WHERE projectId = :projectId")
    void updateStatus(String projectId, String status, long updatedAt);
}
