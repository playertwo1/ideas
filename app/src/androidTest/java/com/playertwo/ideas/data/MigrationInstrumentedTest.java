package com.playertwo.ideas.data;

import android.database.Cursor;
import java.util.Scanner;
import org.json.JSONObject;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MigrationInstrumentedTest {
    @Rule public MigrationTestHelper helper = new MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(), IdeaDatabase.class);

    @Test public void v1ToV2PreservesIdsTextsAndRelations() throws Exception {
        String name = "f03-migration.db";
        Scanner input = new Scanner(InstrumentationRegistry.getInstrumentation().getContext()
            .getAssets().open("f03_migration_v1.json"), "UTF-8").useDelimiter("\\A");
        JSONObject fixture = new JSONObject(input.next());
        input.close();
        JSONObject project = fixture.getJSONArray("projects").getJSONObject(0);
        JSONObject draft = fixture.getJSONArray("drafts").getJSONObject(0);
        JSONObject event = fixture.getJSONArray("events").getJSONObject(0);
        SupportSQLiteDatabase old = helper.createDatabase(name, 1);
        old.execSQL("INSERT INTO projects(projectId,title,status,updatedAt) VALUES(?,?,?,?)",
            new Object[]{project.getString("projectId"), project.getString("title"), project.getString("status"), 1});
        old.execSQL("INSERT INTO drafts(projectId,content,updatedAt) VALUES(?,?,?)",
            new Object[]{draft.getString("projectId"), draft.getString("content"), draft.getLong("updatedAt")});
        old.execSQL("INSERT INTO project_events(eventId,projectId,type,createdAt) VALUES(?,?,?,?)",
            new Object[]{17, event.getString("projectId"), event.getString("type"), event.getLong("createdAt")});
        old.execSQL("INSERT INTO project_events(eventId,projectId,type,createdAt) VALUES(18,'P-001',NULL,4)");
        old.close();
        SupportSQLiteDatabase migrated = helper.runMigrationsAndValidate(name, 2, true, IdeaDatabase.MIGRATION_1_2);
        try (Cursor c = migrated.query("SELECT projectId,title,status,archivedAt FROM projects WHERE projectId='P-001'")) {
            assertTrue(c.moveToFirst()); assertEquals("P-001", c.getString(0)); assertEquals("Projeto teste", c.getString(1));
            assertEquals("ACTIVE", c.getString(2)); assertEquals(0, c.getLong(3));
        }
        try (Cursor c = migrated.query("SELECT content FROM drafts WHERE projectId='P-001'")) {
            assertTrue(c.moveToFirst()); assertEquals("rascunho", c.getString(0));
        }
        try (Cursor c = migrated.query("SELECT eventId,type FROM project_events WHERE projectId='P-001'")) {
            assertTrue(c.moveToFirst()); assertEquals(17, c.getLong(0)); assertEquals("CREATED", c.getString(1));
            assertTrue(c.moveToNext()); assertEquals(18, c.getLong(0)); assertNull(c.getString(1));
        }
        try (Cursor c = migrated.query("SELECT revision,title,status FROM project_revisions WHERE projectId='P-001'")) {
            assertTrue(c.moveToFirst()); assertEquals(1, c.getInt(0));
            assertEquals("Projeto teste", c.getString(1)); assertEquals("ACTIVE", c.getString(2));
        }
        migrated.close();
    }
}
