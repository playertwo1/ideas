package com.playertwo.ideas.data;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
        JSONObject fixture = new JSONObject(readAsset("f09/f09-01-orphan-item.json"));
        String projectId = fixture.getString("projectId");
        String phaseId = fixture.getString("phaseId");
        String itemId = fixture.getString("itemId");
        assertTrue(fixture.getString("expected").contains("reject"));
        ProjectEntity project = new ProjectEntity(projectId, "Projeto", "ACTIVE", 1L);
        ProjectIntakeEntity snapshot = new ProjectIntakeEntity(projectId, "Título", "ideia", "", "hash",
            "sugestão", "APP", "", "LIGHT", "motivo", true, 80,
            "Aguardando próxima fase", 1L);
        PhaseEntity phase = new PhaseEntity(projectId, "PH-1", "Fase", "Objetivo", "MVP", 1,
            1, "PROPOSED", "fixture", 1L);
        RoadmapItemEntity orphan = new RoadmapItemEntity(projectId, itemId, phaseId, "Órfão",
            "Objetivo", "Entrega", "Verificação", "MVP", "MUST", "", 0, 1,
            "PROPOSED", "fixture", 1L);
        try {
            MarkdownRenderer.render(project, snapshot, Collections.singletonList(phase),
                Collections.singletonList(orphan));
            fail("orphan fixture was accepted");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains(phaseId));
        }
    }

    private static String readAsset(String path) throws Exception {
        try (InputStream stream = InstrumentationRegistry.getInstrumentation().getContext().getAssets().open(path);
             InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            StringBuilder content = new StringBuilder();
            char[] buffer = new char[256];
            int count;
            while ((count = reader.read(buffer)) >= 0) content.append(buffer, 0, count);
            return content.toString();
        }
    }
}
