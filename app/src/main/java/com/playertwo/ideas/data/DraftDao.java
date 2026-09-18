package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DraftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) void save(DraftEntity draft);
    @Query("SELECT * FROM drafts WHERE projectId = :id") DraftEntity find(String id);
    @Query("SELECT * FROM drafts ORDER BY projectId") List<DraftEntity> all();
    @Query("DELETE FROM drafts WHERE projectId = :id") int delete(String id);
}
