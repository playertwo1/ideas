package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ScopeItemDao {
    @Query("SELECT * FROM scope_items WHERE projectId = :projectId ORDER BY itemId ASC")
    List<ScopeItemEntity> forProject(String projectId);

    @Query("SELECT * FROM scope_items WHERE projectId = :projectId AND itemId = :itemId")
    ScopeItemEntity find(String projectId, String itemId);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(ScopeItemEntity entity);

    @Query("UPDATE scope_items SET status = :status, updatedAt = :updatedAt WHERE projectId = :projectId AND status = 'PROPOSED'")
    void acceptProposed(String projectId, String status, long updatedAt);
}
