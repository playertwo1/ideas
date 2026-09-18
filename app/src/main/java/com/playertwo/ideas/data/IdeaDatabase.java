package com.playertwo.ideas.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ProjectEntity.class, ProjectEventEntity.class, DraftEntity.class}, version = 1, exportSchema = true)
public abstract class IdeaDatabase extends RoomDatabase {
    public abstract ProjectDao projects();
    public abstract DraftDao drafts();
    public abstract EventDao events();
    public static IdeaDatabase open(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), IdeaDatabase.class, "idea.db").build();
    }
}
