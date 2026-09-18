package com.playertwo.ideas.data;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CorruptionInstrumentedTest {
    @Test public void corruptExistingDatabaseIsRejectedWithoutReplacement() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        File file = context.getDatabasePath("idea.db");
        file.getParentFile().mkdirs();
        context.deleteDatabase("idea.db");
        byte[] corrupt = "not a sqlite database".getBytes(StandardCharsets.UTF_8);
        Files.write(file.toPath(), corrupt);
        try {
            IdeaDatabase db = IdeaDatabase.open(context);
            db.projects().all();
            db.close();
            fail("corrupt database accepted");
        } catch (RuntimeException expected) {
            assertArrayEquals(corrupt, Files.readAllBytes(file.toPath()));
        } finally { context.deleteDatabase("idea.db"); }
    }
}
