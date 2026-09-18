package com.playertwo.ideas.data;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PersistenceInstrumentedTest {
    private IdeaDatabase db;
    @Before public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, IdeaDatabase.class).allowMainThreadQueries().build();
    }
    @After public void tearDown() { db.close(); }
    @Test public void projectIdsRemainIsolated() {
        db.projects().insert(new ProjectEntity("P1", "Um", "ACTIVE", 1));
        db.projects().insert(new ProjectEntity("P2", "Dois", "ACTIVE", 1));
        assertEquals("Um", db.projects().find("P1").title);
        assertEquals("Dois", db.projects().find("P2").title);
        assertNull(db.projects().find("P3"));
        db.drafts().save(new DraftEntity("P1", "rascunho um", 2));
        db.drafts().save(new DraftEntity("P2", "rascunho dois", 2));
        db.events().insert(new ProjectEventEntity("P1", "ONE", 3));
        db.events().insert(new ProjectEventEntity("P2", "TWO", 3));
        assertEquals("rascunho um", db.drafts().find("P1").content);
        assertEquals("rascunho dois", db.drafts().find("P2").content);
        assertEquals("ONE", db.events().forProject("P1").get(0).type);
        assertEquals("TWO", db.events().forProject("P2").get(0).type);
    }
    @Test public void orphanDraftAndEventAreRejectedBySqlite() {
        try { db.drafts().save(new DraftEntity("missing", "orphan", 1)); fail("orphan draft accepted"); }
        catch (android.database.sqlite.SQLiteConstraintException expected) { }
        try { db.events().insert(new ProjectEventEntity("missing", "ORPHAN", 1)); fail("orphan event accepted"); }
        catch (android.database.sqlite.SQLiteConstraintException expected) { }
    }
    @Test public void archiveRestoreDeleteUseExactId() {
        db.projects().insert(new ProjectEntity("P1", "Um", "ACTIVE", 1));
        db.projects().insert(new ProjectEntity("P2", "Dois", "ACTIVE", 1));
        ProjectRepository repository = new ProjectRepository(db);
        repository.archive("P1");
        assertEquals("ARCHIVED", db.projects().find("P1").status);
        assertEquals("ACTIVE", db.projects().find("P2").status);
        repository.restore("P1");
        repository.deletePermanently("P1");
        assertNull(db.projects().find("P1"));
        assertTrue(db.revisions().forProject("P1").isEmpty());
        assertTrue(db.events().forProject("P1").isEmpty());
        assertNotNull(db.projects().find("P2"));
    }
    @Test public void failedEventInsertRollsBackProjectWrite() {
        ProjectRepository repository = new ProjectRepository(db);
        try {
            repository.saveRevision(new ProjectEntity("P1", "Um", "ACTIVE", 1),
                new ProjectEventEntity("P1", null, 1));
            fail("invalid event was accepted");
        } catch (RuntimeException expected) { }
        assertNull(db.projects().find("P1"));
    }
    @Test public void mismatchedEventIsRejected() {
        ProjectRepository repository = new ProjectRepository(db);
        try {
            repository.saveRevision(new ProjectEntity("P1", "Um", "ACTIVE", 1),
                new ProjectEventEntity("P2", "CREATED", 1));
            fail("mismatched event was accepted");
        } catch (IllegalArgumentException expected) { }
        assertNull(db.projects().find("P1"));
    }
    @Test public void acceptedRevisionAndEventAreAtomic() {
        new ProjectRepository(db).saveRevision(new ProjectEntity("P1", "Um", "ACTIVE", 1),
            new ProjectEventEntity("P1", "CREATED", 1));
        new ProjectRepository(db).saveRevision(new ProjectEntity("P1", "Dois", "ACTIVE", 2),
            new ProjectEventEntity("P1", "RENAMED", 2));
        assertEquals(2, db.revisions().forProject("P1").size());
        assertEquals("Um", db.revisions().forProject("P1").get(0).title);
        assertEquals("Dois", db.revisions().forProject("P1").get(1).title);
        assertEquals("Dois", db.projects().find("P1").title);
        assertEquals(2, db.events().forProject("P1").size());
    }
    @Test public void draftSurvivesDatabaseReopen() {
        Context context = ApplicationProvider.getApplicationContext();
        context.deleteDatabase("f03-recovery.db");
        IdeaDatabase first = Room.databaseBuilder(context, IdeaDatabase.class, "f03-recovery.db")
            .allowMainThreadQueries().build();
        first.projects().insert(new ProjectEntity("P1", "Um", "ACTIVE", 1));
        new ProjectRepository(first).saveDraft("P1", "texto confirmado", 2);
        first.close();
        IdeaDatabase second = Room.databaseBuilder(context, IdeaDatabase.class, "f03-recovery.db")
            .allowMainThreadQueries().build();
        assertEquals("texto confirmado", second.drafts().find("P1").content);
        assertNull(second.drafts().find("P2"));
        second.close();
        context.deleteDatabase("f03-recovery.db");
    }
}
