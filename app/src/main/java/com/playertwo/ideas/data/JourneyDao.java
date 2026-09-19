package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface JourneyDao {
    @Query("SELECT * FROM journeys WHERE projectId = :projectId ORDER BY journeyId ASC")
    List<JourneyEntity> forProject(String projectId);

    @Query("SELECT * FROM journeys WHERE projectId = :projectId AND journeyId = :journeyId")
    JourneyEntity find(String projectId, String journeyId);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(JourneyEntity entity);

    @Query("UPDATE journeys SET status = :status, updatedAt = :updatedAt WHERE projectId = :projectId AND status = 'PROPOSED'")
    void acceptProposed(String projectId, String status, long updatedAt);
}
