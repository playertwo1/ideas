package com.playertwo.ideas.data;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class F09RendererInstrumentedTest {
    @Test public void f09SnapshotFixtureIsPackagedForExternalVerification() throws Exception {
        try (InputStream stream = InstrumentationRegistry.getInstrumentation().getContext()
            .getAssets().open("f09/f09-01-snapshot.json")) {
            assertTrue(stream.read() >= 0);
        }
    }
}
