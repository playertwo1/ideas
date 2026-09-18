package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DecisionRevisionDao {
    @Insert(onConflict = OnConflictStrategy.ABORT) void insert(DecisionRevisionEntity revision);
    @Query("SELECT * FROM decision_revisions WHERE projectId = :projectId ORDER BY revision DESC LIMIT 1") DecisionRevisionEntity latest(String projectId);
    @Query("UPDATE decision_revisions SET derivedValid = 0 WHERE projectId = :projectId") int invalidateAll(String projectId);
}
