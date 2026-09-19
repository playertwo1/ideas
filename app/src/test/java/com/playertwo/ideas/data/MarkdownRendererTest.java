package com.playertwo.ideas.data;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

public class MarkdownRendererTest {
    @Test public void acceptedSnapshotRendersDeterministicallyWithRelationships() {
        ProjectEntity project = new ProjectEntity("P1", "Projeto café", "ACTIVE", 10L);
        ProjectIntakeEntity snapshot = new ProjectIntakeEntity(
            "P1", "Título", "linha 1\nlinha 2", "Sem Firebase", "hash-1",
            "Interpretação ✓", "APP", "offline", "STANDARD", "motivo",
            true, 80, "Aguardando próxima fase", 10L);
        PhaseEntity phase = new PhaseEntity("P1", "PH-1", "Fase 1", "Objetivo", "MVP", 1,
            1, "PROPOSED", "manual", 10L);
        RoadmapItemEntity item = new RoadmapItemEntity("P1", "ITEM-1", "PH-1", "Captura",
            "Capturar", "Tela", "Teste", "MVP", "MUST", "", 0, 1,
            "PROPOSED", "manual", 10L);

        String first = MarkdownRenderer.render(project, snapshot,
            Collections.singletonList(phase), Collections.singletonList(item));
        String second = MarkdownRenderer.render(project, snapshot,
            Collections.singletonList(phase), Collections.singletonList(item));

        assertEquals(first, second);
        assertTrue(first.contains("projectId: P1"));
        assertTrue(first.contains("Projeto café"));
        assertTrue(first.contains("Interpretação ✓"));
        assertTrue(first.contains("ITEM-1"));
        assertTrue(first.contains("phaseId: PH-1"));
        assertTrue(first.contains("status: PROPOSED"));
    }

    @Test public void rendererRejectsUnacceptedOrForeignSnapshotWithoutRendering() {
        ProjectEntity project = new ProjectEntity("P1", "Projeto", "ACTIVE", 10L);
        ProjectIntakeEntity unaccepted = new ProjectIntakeEntity(
            "P1", "Título", "ideia", "", "hash", "sugestão", "APP", "", "LIGHT", "motivo",
            false, 60, "Aceitar ou rejeitar sugestão", 10L);
        try {
            MarkdownRenderer.render(project, unaccepted, Collections.emptyList(), Collections.emptyList());
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("accepted"));
        }

        ProjectIntakeEntity foreign = new ProjectIntakeEntity(
            "P2", "Título", "ideia", "", "hash", "sugestão", "APP", "", "LIGHT", "motivo",
            true, 80, "Aguardando próxima fase", 10L);
        try {
            MarkdownRenderer.render(project, foreign, Collections.emptyList(), Collections.emptyList());
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("projectId"));
        }
    }

    @Test public void rendererRejectsOrphanRoadmapItemInsteadOfDroppingIt() {
        ProjectEntity project = new ProjectEntity("P1", "Projeto", "ACTIVE", 10L);
        ProjectIntakeEntity snapshot = new ProjectIntakeEntity(
            "P1", "Título", "ideia", "", "hash", "sugestão", "APP", "", "LIGHT", "motivo",
            true, 80, "Aguardando próxima fase", 10L);
        PhaseEntity phase = new PhaseEntity("P1", "PH-1", "Fase", "Objetivo", "MVP", 1,
            1, "PROPOSED", "manual", 10L);
        RoadmapItemEntity orphan = new RoadmapItemEntity("P1", "ITEM-ORPHAN", "PH-MISSING", "Órfão",
            "Objetivo", "Entrega", "Verificação", "MVP", "MUST", "", 0, 1,
            "PROPOSED", "fixture", 10L);
        try {
            MarkdownRenderer.render(project, snapshot, Collections.singletonList(phase),
                Collections.singletonList(orphan));
            fail("orphan roadmap item was silently dropped");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("phaseId"));
            assertTrue(expected.getMessage().contains("PH-MISSING"));
        }
    }
}
