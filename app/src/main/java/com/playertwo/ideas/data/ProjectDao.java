package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ProjectDao {
    @Insert void insert(ProjectEntity project);
    @Query("SELECT * FROM projects WHERE projectId = :id") ProjectEntity find(String id);
    @Query("SELECT * FROM projects ORDER BY projectId") List<ProjectEntity> all();
    @Query("UPDATE projects SET status = :status, updatedAt = :updatedAt WHERE projectId = :id") int updateStatus(String id, String status, long updatedAt);
    @Query("UPDATE projects SET title = :title, status = :status, updatedAt = :updatedAt WHERE projectId = :id")
    int updateRevision(String id, String title, String status, long updatedAt);
    @Query("DELETE FROM projects WHERE projectId = :id") int delete(String id);
}
