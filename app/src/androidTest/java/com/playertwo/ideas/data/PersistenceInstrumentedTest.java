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
        assertNotNull(db.projects().find("P2"));
    }
}
