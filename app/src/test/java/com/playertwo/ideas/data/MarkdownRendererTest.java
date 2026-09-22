package com.playertwo.ideas.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            fail("unaccepted snapshot was rendered");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("accepted"));
        }

        ProjectIntakeEntity foreign = new ProjectIntakeEntity(
            "P2", "Título", "ideia", "", "hash", "sugestão", "APP", "", "LIGHT", "motivo",
            true, 80, "Aguardando próxima fase", 10L);
        try {
            MarkdownRenderer.render(project, foreign, Collections.emptyList(), Collections.emptyList());
            fail("foreign project snapshot was rendered");
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

    @Test public void rendererRejectsDuplicatePhaseId() {
        ProjectEntity project = new ProjectEntity("P1", "Projeto", "ACTIVE", 10L);
        ProjectIntakeEntity snapshot = new ProjectIntakeEntity(
            "P1", "Título", "ideia", "", "hash", "sugestão", "APP", "", "LIGHT", "motivo",
            true, 80, "Aguardando próxima fase", 10L);
        PhaseEntity phase1 = new PhaseEntity("P1", "PH-1", "Fase 1", "Objetivo", "MVP", 1,
            1, "PROPOSED", "manual", 10L);
        PhaseEntity phase2 = new PhaseEntity("P1", "PH-1", "Fase 2", "Objetivo", "MVP", 2,
            1, "PROPOSED", "manual", 10L);
        try {
            MarkdownRenderer.render(project, snapshot, Arrays.asList(phase1, phase2), Collections.emptyList());
            fail("duplicate phaseId was accepted");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("duplicate phaseId"));
        }
    }

    @Test public void rendererRejectsDuplicateItemId() {
        ProjectEntity project = new ProjectEntity("P1", "Projeto", "ACTIVE", 10L);
        ProjectIntakeEntity snapshot = new ProjectIntakeEntity(
            "P1", "Título", "ideia", "", "hash", "sugestão", "APP", "", "LIGHT", "motivo",
            true, 80, "Aguardando próxima fase", 10L);
        PhaseEntity phase = new PhaseEntity("P1", "PH-1", "Fase", "Objetivo", "MVP", 1,
            1, "PROPOSED", "manual", 10L);
        RoadmapItemEntity item1 = new RoadmapItemEntity("P1", "ITEM-1", "PH-1", "Item 1",
            "Objetivo", "Entrega", "Verificação", "MVP", "MUST", "", 0, 1,
            "PROPOSED", "manual", 10L);
        RoadmapItemEntity item2 = new RoadmapItemEntity("P1", "ITEM-1", "PH-1", "Item 2",
            "Objetivo", "Entrega", "Verificação", "MVP", "MUST", "", 1, 1,
            "PROPOSED", "manual", 10L);
        try {
            MarkdownRenderer.render(project, snapshot, Collections.singletonList(phase),
                Arrays.asList(item1, item2));
            fail("duplicate itemId was accepted");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("duplicate itemId"));
        }
    }

    @Test public void rendererIsDeterministicDespiteInputOrder() {
        ProjectEntity project = new ProjectEntity("P1", "Projeto", "ACTIVE", 10L);
        ProjectIntakeEntity snapshot = new ProjectIntakeEntity(
            "P1", "Título", "ideia", "", "hash", "sugestão", "APP", "", "LIGHT", "motivo",
            true, 80, "Aguardando próxima fase", 10L);
        PhaseEntity phase1 = new PhaseEntity("P1", "PH-1", "Fase 1", "Objetivo", "MVP", 1,
            1, "PROPOSED", "manual", 10L);
        PhaseEntity phase2 = new PhaseEntity("P1", "PH-2", "Fase 2", "Objetivo", "MVP", 2,
            1, "PROPOSED", "manual", 10L);
        RoadmapItemEntity item1 = new RoadmapItemEntity("P1", "ITEM-1", "PH-1", "Item 1",
            "Objetivo", "Entrega", "Verificação", "MVP", "MUST", "", 0, 1,
            "PROPOSED", "manual", 10L);
        RoadmapItemEntity item2 = new RoadmapItemEntity("P1", "ITEM-2", "PH-2", "Item 2",
            "Objetivo", "Entrega", "Verificação", "MVP", "MUST", "", 0, 1,
            "PROPOSED", "manual", 10L);

        List<PhaseEntity> phasesList1 = Arrays.asList(phase1, phase2);
        List<RoadmapItemEntity> itemsList1 = Arrays.asList(item1, item2);
        String first = MarkdownRenderer.render(project, snapshot, phasesList1, itemsList1);

        List<PhaseEntity> phasesList2 = Arrays.asList(phase2, phase1);
        List<RoadmapItemEntity> itemsList2 = Arrays.asList(item2, item1);
        String second = MarkdownRenderer.render(project, snapshot, phasesList2, itemsList2);

        assertEquals("output must be byte-identical despite input order", first, second);
    }
}
