package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DecisionHistoryDao {
    @Insert void insert(DecisionHistoryEntity event);
    @Query("SELECT * FROM decision_history WHERE projectId = :projectId ORDER BY historyId") List<DecisionHistoryEntity> forProject(String projectId);
}
