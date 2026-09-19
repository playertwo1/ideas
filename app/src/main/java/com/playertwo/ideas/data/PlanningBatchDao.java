package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PlanningBatchDao {
    @Query("SELECT * FROM planning_batches WHERE projectId = :projectId AND batchId = :batchId")
    PlanningBatchEntity find(String projectId, String batchId);

    @Query("SELECT * FROM planning_batches WHERE projectId = :projectId ORDER BY createdAt DESC")
    List<PlanningBatchEntity> forProject(String projectId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlanningBatchEntity entity);

    @Query("UPDATE planning_batches SET status = :status WHERE projectId = :projectId AND batchId = :batchId")
    void updateStatus(String projectId, String batchId, String status);
}
