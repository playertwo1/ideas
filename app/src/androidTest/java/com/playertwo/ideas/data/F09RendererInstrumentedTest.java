package com.playertwo.ideas.data;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.Collections;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class F09RendererInstrumentedTest {
    @Test public void f09SnapshotFixtureIsPackagedForExternalVerification() throws Exception {
        try (InputStream stream = InstrumentationRegistry.getInstrumentation().getContext()
            .getAssets().open("f09/f09-01-snapshot.json")) {
            assertTrue(stream.read() >= 0);
        }
    }

    @Test public void orphanFixtureIsRejectedByRealRenderer() throws Exception {
        try (InputStream stream = InstrumentationRegistry.getInstrumentation().getContext()
            .getAssets().open("f09/f09-01-orphan-item.json")) {
            assertTrue(stream.read() >= 0);
        }
        ProjectEntity project = new ProjectEntity("P1", "Projeto", "ACTIVE", 1L);
        ProjectIntakeEntity snapshot = new ProjectIntakeEntity("P1", "Título", "ideia", "", "hash",
            "sugestão", "APP", "", "LIGHT", "motivo", true, 80,
            "Aguardando próxima fase", 1L);
        PhaseEntity phase = new PhaseEntity("P1", "PH-1", "Fase", "Objetivo", "MVP", 1,
            1, "PROPOSED", "fixture", 1L);
        RoadmapItemEntity orphan = new RoadmapItemEntity("P1", "ITEM-ORPHAN", "PH-MISSING", "Órfão",
            "Objetivo", "Entrega", "Verificação", "MVP", "MUST", "", 0, 1,
            "PROPOSED", "fixture", 1L);
        try {
            MarkdownRenderer.render(project, snapshot, Collections.singletonList(phase),
                Collections.singletonList(orphan));
            fail("orphan fixture was accepted");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("PH-MISSING"));
        }
    }
}
