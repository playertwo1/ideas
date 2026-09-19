package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FlowStepDao {
    @Query("SELECT * FROM flow_steps WHERE projectId = :projectId ORDER BY stepOrder ASC")
    List<FlowStepEntity> forProject(String projectId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FlowStepEntity entity);

    @Query("UPDATE flow_steps SET status = :status, updatedAt = :updatedAt WHERE projectId = :projectId AND status = 'PROPOSED'")
    void acceptProposed(String projectId, String status, long updatedAt);
}
