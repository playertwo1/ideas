package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RequirementDao {
    @Query("SELECT * FROM requirements WHERE projectId = :projectId ORDER BY reqId ASC")
    List<RequirementEntity> forProject(String projectId);

    @Query("SELECT * FROM requirements WHERE projectId = :projectId AND reqId = :reqId")
    RequirementEntity find(String projectId, String reqId);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(RequirementEntity entity);

    @Query("UPDATE requirements SET status = :status, updatedAt = :updatedAt WHERE projectId = :projectId AND status = 'PROPOSED'")
    void acceptProposed(String projectId, String status, long updatedAt);
}
