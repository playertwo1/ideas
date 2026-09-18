package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ProjectDao {
    @Insert void insert(ProjectEntity project);
    @Query("SELECT * FROM projects WHERE projectId = :id") ProjectEntity find(String id);
    @Query("UPDATE projects SET status = :status, updatedAt = :updatedAt WHERE projectId = :id") int updateStatus(String id, String status, long updatedAt);
    @Query("DELETE FROM projects WHERE projectId = :id") int delete(String id);
}
