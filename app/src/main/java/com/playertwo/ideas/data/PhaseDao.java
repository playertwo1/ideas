package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PhaseDao {
    @Query("SELECT * FROM roadmap_phases WHERE projectId = :projectId ORDER BY priority ASC, phaseId ASC")
    List<PhaseEntity> forProject(String projectId);
    @Query("SELECT * FROM roadmap_phases WHERE projectId = :projectId AND phaseId = :phaseId")
    PhaseEntity find(String projectId, String phaseId);
    @Insert(onConflict = OnConflictStrategy.ABORT) void insert(PhaseEntity entity);
    @Query("UPDATE roadmap_phases SET priority = :priority, updatedAt = :updatedAt WHERE projectId = :projectId AND phaseId = :phaseId")
    int updatePriority(String projectId, String phaseId, int priority, long updatedAt);
}
