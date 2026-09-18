package com.playertwo.ideas.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;

@Database(entities = {ProjectEntity.class, ProjectEventEntity.class, DraftEntity.class, ProjectRevisionEntity.class}, version = 2, exportSchema = true)
public abstract class IdeaDatabase extends RoomDatabase {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("ALTER TABLE projects ADD COLUMN archivedAt INTEGER NOT NULL DEFAULT 0");
            db.execSQL("CREATE TABLE drafts_new (projectId TEXT NOT NULL PRIMARY KEY, content TEXT, updatedAt INTEGER NOT NULL, FOREIGN KEY(projectId) REFERENCES projects(projectId) ON DELETE CASCADE)");
            db.execSQL("INSERT INTO drafts_new(projectId,content,updatedAt) SELECT projectId,content,updatedAt FROM drafts");
            db.execSQL("DROP TABLE drafts");
            db.execSQL("ALTER TABLE drafts_new RENAME TO drafts");
            db.execSQL("CREATE TABLE project_events_new (eventId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, projectId TEXT NOT NULL, type TEXT, createdAt INTEGER NOT NULL, FOREIGN KEY(projectId) REFERENCES projects(projectId) ON DELETE CASCADE)");
            db.execSQL("INSERT INTO project_events_new(eventId,projectId,type,createdAt) SELECT eventId,projectId,type,createdAt FROM project_events");
            db.execSQL("DROP TABLE project_events");
            db.execSQL("ALTER TABLE project_events_new RENAME TO project_events");
            db.execSQL("CREATE INDEX index_project_events_projectId ON project_events(projectId)");
            db.execSQL("CREATE TABLE IF NOT EXISTS project_revisions (projectId TEXT NOT NULL, revision INTEGER NOT NULL, title TEXT, status TEXT, updatedAt INTEGER NOT NULL, PRIMARY KEY(projectId, revision), FOREIGN KEY(projectId) REFERENCES projects(projectId) ON DELETE CASCADE)");
            db.execSQL("INSERT INTO project_revisions(projectId,revision,title,status,updatedAt) SELECT projectId,1,title,status,updatedAt FROM projects");
            db.execSQL("CREATE INDEX index_project_revisions_projectId ON project_revisions(projectId)");
        }
    };
    public abstract ProjectDao projects();
    public abstract DraftDao drafts();
    public abstract EventDao events();
    public abstract RevisionDao revisions();
    public static IdeaDatabase open(Context context) {
        File file = context.getDatabasePath("idea.db");
        if (file.exists()) {
            try (SQLiteDatabase existing = SQLiteDatabase.openDatabase(file.getPath(), null, SQLiteDatabase.OPEN_READONLY);
                 Cursor check = existing.rawQuery("PRAGMA quick_check", null)) {
                if (!check.moveToFirst() || !"ok".equalsIgnoreCase(check.getString(0)))
                    throw new IllegalStateException("database integrity check failed");
            } catch (RuntimeException error) {
                throw new IllegalStateException("existing database unreadable; original preserved", error);
            }
        }
        return Room.databaseBuilder(context.getApplicationContext(), IdeaDatabase.class, "idea.db")
            .addMigrations(MIGRATION_1_2).build();
    }
}
