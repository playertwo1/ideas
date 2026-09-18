package com.playertwo.ideas.data;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessRecoveryInstrumentedTest {
    private static final String NAME = "f03-process-recovery.db";

    @Test public void stage1_saveConfirmedDraft() {
        Context context = ApplicationProvider.getApplicationContext();
        context.deleteDatabase(NAME);
        IdeaDatabase db = Room.databaseBuilder(context, IdeaDatabase.class, NAME)
            .addMigrations(IdeaDatabase.MIGRATION_1_2).allowMainThreadQueries().build();
        db.projects().insert(new ProjectEntity("P1", "Persistente", "ACTIVE", 1));
        new ProjectRepository(db).saveDraft("P1", "confirmado", 2);
        db.close();
    }

    @Test public void stage2_recoverAfterExternalForceStop() {
        Context context = ApplicationProvider.getApplicationContext();
        IdeaDatabase db = Room.databaseBuilder(context, IdeaDatabase.class, NAME)
            .addMigrations(IdeaDatabase.MIGRATION_1_2).allowMainThreadQueries().build();
        assertEquals("Persistente", db.projects().find("P1").title);
        assertEquals("confirmado", db.drafts().find("P1").content);
        assertNull(db.drafts().find("P2"));
        db.close();
    }
}
