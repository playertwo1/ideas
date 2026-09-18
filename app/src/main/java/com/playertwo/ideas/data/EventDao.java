package com.playertwo.ideas.data;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface EventDao { @Insert void insert(ProjectEventEntity event); }
