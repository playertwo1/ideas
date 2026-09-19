package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RoadmapItemDao {
    @Query("SELECT * FROM roadmap_items WHERE projectId = :projectId ORDER BY orderIndex ASC, itemId ASC")
    List<RoadmapItemEntity> forProject(String projectId);
    @Query("SELECT * FROM roadmap_items WHERE projectId = :projectId AND itemId = :itemId")
    RoadmapItemEntity find(String projectId, String itemId);
    @Insert(onConflict = OnConflictStrategy.ABORT) void insert(RoadmapItemEntity entity);
    @Query("UPDATE roadmap_items SET orderIndex = :orderIndex, updatedAt = :updatedAt WHERE projectId = :projectId AND itemId = :itemId")
    int updateOrder(String projectId, String itemId, int orderIndex, long updatedAt);
    @Query("UPDATE roadmap_items SET priority = :priority, updatedAt = :updatedAt WHERE projectId = :projectId AND itemId = :itemId")
    int updatePriority(String projectId, String itemId, String priority, long updatedAt);
}
