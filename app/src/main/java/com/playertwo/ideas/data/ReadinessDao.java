package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ReadinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(ReadinessSnapshotEntity entity);
    @Query("SELECT * FROM roadmap_readiness WHERE projectId = :projectId ORDER BY createdAt DESC LIMIT 1")
    ReadinessSnapshotEntity latest(String projectId);
}
