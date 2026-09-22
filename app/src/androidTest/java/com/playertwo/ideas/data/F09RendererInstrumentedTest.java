package com.playertwo.ideas.data;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class F09RendererInstrumentedTest {
    @Test public void f09SnapshotFixtureRendersToCanonicalMarkdown() throws Exception {
        String snapshotJson = readAsset("f09/f09-01-snapshot.json");
        String expectedMarkdown = readAsset("f09/f09-01-snapshot.md");
        JSONObject snapshot = new JSONObject(snapshotJson);

        String projectId = snapshot.getString("projectId");
        ProjectEntity project = new ProjectEntity(projectId, snapshot.getString("projectTitle"),
            snapshot.getString("projectStatus"), 1L);
        ProjectIntakeEntity intake = new ProjectIntakeEntity(projectId,
            snapshot.getString("originalTitle"), snapshot.getString("originalIdea"),
            snapshot.getString("originalLimits"), snapshot.getString("originalHash"),
            snapshot.getString("interpretation"), snapshot.getString("projectType"),
            snapshot.getString("restrictions"), snapshot.getString("depthMode"),
            snapshot.getString("suggestionReason"), true, 80, "ACCEPTED", 1L);

        List<PhaseEntity> phases = new ArrayList<>();
        JSONArray phasesArray = snapshot.getJSONArray("phases");
        for (int i = 0; i < phasesArray.length(); i++) {
            JSONObject p = phasesArray.getJSONObject(i);
            PhaseEntity phase = new PhaseEntity(projectId, p.getString("phaseId"),
                p.getString("title"), p.getString("objective"), p.getString("track"),
                p.getInt("priority"), p.getInt("revision"), p.getString("status"),
                p.getString("sourceRef"), 1L);
            phases.add(phase);
        }

        List<RoadmapItemEntity> items = new ArrayList<>();
        JSONArray itemsArray = snapshot.getJSONArray("items");
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject it = itemsArray.getJSONObject(i);
            RoadmapItemEntity item = new RoadmapItemEntity(projectId, it.getString("itemId"),
                it.getString("phaseId"), it.getString("title"), it.getString("objective"),
                it.getString("delivery"), it.getString("verify"), it.getString("track"),
                it.getString("priority"), it.getString("dependencies"), it.getInt("orderIndex"),
                it.getInt("revision"), it.getString("status"), it.getString("sourceRef"), 1L);
            items.add(item);
        }

        String rendered = MarkdownRenderer.render(project, intake, phases, items);
        assertEquals("fixture must render to canonical golden markdown", expectedMarkdown, rendered);
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
