package com.playertwo.ideas.data;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BackupInstrumentedTest {
    @Test public void validBackupRestoresProjectsDraftsAndEvents() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        File target = new File(context.getCacheDir(), "f03-backup.json");
        IdeaDatabase source = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
        source.projects().insert(new ProjectEntity("P1", "Um", "ACTIVE", 1));
        source.drafts().save(new DraftEntity("P1", "texto", 2));
        source.events().insert(new ProjectEventEntity("P1", "CREATED", 3));
        source.revisions().insert(new ProjectRevisionEntity("P1", 1, "Um", "ACTIVE", 1));
        ProjectBackup.write(source, target);
        IdeaDatabase restored = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
        ProjectBackup.restore(restored, target);
        assertEquals("Um", restored.projects().find("P1").title);
        assertEquals("texto", restored.drafts().find("P1").content);
        assertEquals(1, restored.events().forProject("P1").size());
        assertEquals("Um", restored.revisions().forProject("P1").get(0).title);
        source.close(); restored.close(); target.delete();
    }
    @Test public void backupPreservesNullableLegacyEventType() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        File target = new File(context.getCacheDir(), "f03-null-event.json");
        IdeaDatabase source = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
        source.projects().insert(new ProjectEntity("P1", "Um", "ACTIVE", 1));
        source.events().insert(new ProjectEventEntity("P1", null, 2));
        ProjectBackup.write(source, target);
        IdeaDatabase restored = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
        ProjectBackup.restore(restored, target);
        assertNull(restored.events().forProject("P1").get(0).type);
        source.close(); restored.close(); target.delete();
    }

    @Test public void corruptBackupLeavesDatabaseUnchanged() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        File target = new File(context.getCacheDir(), "f03-corrupt.json");
        IdeaDatabase db = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
        db.projects().insert(new ProjectEntity("P1", "Original", "ACTIVE", 1));
        ProjectBackup.write(db, target);
        Files.write(target.toPath(), "invalid backup".getBytes(StandardCharsets.UTF_8));
        try { ProjectBackup.restore(db, target); fail("corruption accepted"); }
        catch (Exception expected) { assertEquals("Original", db.projects().find("P1").title); }
        db.close(); target.delete();
    }

    @Test public void unwritableTargetPreservesExistingBackup() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        File target = new File(context.getCacheDir(), "f03-existing.json");
        IdeaDatabase db = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
        db.projects().insert(new ProjectEntity("P1", "Original", "ACTIVE", 1));
        ProjectBackup.write(db, target);
        byte[] before = Files.readAllBytes(target.toPath());
        File badTarget = new File(target, "child");
        try { ProjectBackup.write(db, badTarget); fail("invalid target accepted"); }
        catch (Exception expected) { assertArrayEquals(before, Files.readAllBytes(target.toPath())); }
        db.close(); target.delete();
    }
    @Test public void simulatedNoSpacePreservesExistingBackup() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        File target = new File(context.getCacheDir(), "f03-no-space.json");
        IdeaDatabase db = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
        db.projects().insert(new ProjectEntity("P1", "Original", "ACTIVE", 1));
        ProjectBackup.write(db, target);
        byte[] before = Files.readAllBytes(target.toPath());
        try {
            ProjectBackup.write(db, target, (path, bytes) -> { throw new IOException("ENOSPC"); });
            fail("no-space error ignored");
        } catch (IOException expected) {
            assertArrayEquals(before, Files.readAllBytes(target.toPath()));
            assertFalse(new File(target.getParentFile(), target.getName() + ".partial").exists());
        }
        db.close(); target.delete();
    }
}
