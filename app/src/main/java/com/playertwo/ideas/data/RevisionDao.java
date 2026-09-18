package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RevisionDao {
    @Insert void insert(ProjectRevisionEntity revision);
    @Query("SELECT COALESCE(MAX(revision), 0) FROM project_revisions WHERE projectId = :id")
    int latest(String id);
    @Query("SELECT * FROM project_revisions WHERE projectId = :id ORDER BY revision")
    List<ProjectRevisionEntity> forProject(String id);
    @Query("SELECT * FROM project_revisions ORDER BY projectId, revision")
    List<ProjectRevisionEntity> all();
}
