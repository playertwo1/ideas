package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EventDao {
    @Insert void insert(ProjectEventEntity event);
    @Query("SELECT * FROM project_events ORDER BY eventId") List<ProjectEventEntity> all();
    @Query("SELECT * FROM project_events WHERE projectId = :projectId ORDER BY eventId") List<ProjectEventEntity> forProject(String projectId);
}
