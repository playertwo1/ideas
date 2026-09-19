package com.playertwo.ideas.data;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class F07PlanningInstrumentedTest {
    private IdeaDatabase db;
    private PlanningRepository repository;

    @Before public void setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), IdeaDatabase.class)
            .allowMainThreadQueries().build();
        db.projects().insert(new ProjectEntity("P1", "Projeto", "ACTIVE", 1));
        db.projects().insert(new ProjectEntity("P2", "Vizinho", "ACTIVE", 1));
        repository = new PlanningRepository(db);
    }

    @After public void tearDown() { db.close(); }

    @Test public void hypothesisDefaultsToNotTestedAndPreservesProjectIsolation() {
        repository.saveHypothesis("P1", "dor", "equipe", "reduzir retrabalho", "teste manual", "tempo", "< 10 min");
        assertEquals("NOT_TESTED", repository.hypothesis("P1").evidenceStatus);
        assertNull(repository.hypothesis("P2"));
    }

    @Test public void mustRequiresRequirementAndScopeRejectsForeignDependency() {
        repository.addScopeItem("P1", "S-001", "MUST", "capturar", "rationale", "", false);
        PlanningPreview preview = repository.preview("P1");
        assertEquals(1, preview.orphanMustIds.size());
        repository.addRequirement("P1", "REQ-001", "REQ", "S-001", "origem", "rationale", "aceite");
        assertEquals(0, repository.preview("P1").orphanMustIds.size());
        try {
            repository.addScopeItem("P1", "S-002", "SHOULD", "fora", "rationale", "P2:S-999", false);
            fail("foreign dependency accepted");
        } catch (IllegalArgumentException expected) { }
    }

    @Test public void flowJourneyAndFailureAreLinkedToRequirement() {
        repository.addScopeItem("P1", "S-001", "MUST", "capturar", "rationale", "", false);
        repository.addRequirement("P1", "REQ-001", "NFR", "S-001", "F07", "rationale", "texto não vazio");
        repository.addJourney("P1", "J-001", "capturar e revisar", "INICIO>REVISAO>EXPORT", "erro de validação", "REQ-001");
        PlanningPreview preview = repository.preview("P1");
        assertEquals(0, preview.orphanMustIds.size());
        assertEquals(0, preview.unlinkedFailureIds.size());
    }

    @Test public void previewIsProposedAndHumanBatchAcceptanceIsExplicit() {
        repository.saveHypothesis("P1", "dor", "equipe", "reduzir retrabalho", "teste manual", "tempo", "< 10 min");
        repository.addScopeItem("P1", "S-001", "MUST", "capturar", "rationale", "", false);
        repository.addRequirement("P1", "REQ-001", "REQ", "S-001", "origem", "rationale", "aceite");
        PlanningPreview first = repository.preview("P1");
        assertTrue(first.isReady);
        assertEquals("PROPOSED", repository.batch("P1", first.batchId).status);
        try { repository.acceptBatch("P1", first.batchId, "AI"); fail("AI accepted batch"); }
        catch (IllegalStateException expected) { }
        repository.acceptBatch("P1", first.batchId, "HUMAN");
        assertEquals("ACCEPTED", repository.batch("P1", first.batchId).status);
    }

    @Test public void humanCannotAcceptPreviewWithCoverageErrors() {
        repository.addScopeItem("P1", "S-100", "MUST", "sem requisito", "rationale", "", false);
        PlanningPreview invalid = repository.preview("P1");
        assertFalse(invalid.isReady);
        try {
            repository.acceptBatch("P1", invalid.batchId, "HUMAN");
            fail("invalid preview accepted");
        } catch (IllegalStateException expected) { }
        assertEquals("PROPOSED", repository.batch("P1", invalid.batchId).status);
    }

    @Test public void journeyRejectsUnknownOrForeignRequirementWithoutMutation() {
        repository.addScopeItem("P2", "S-200", "MUST", "vizinho", "rationale", "", false);
        repository.addRequirement("P2", "REQ-200", "REQ", "S-200", "origem", "rationale", "aceite");
        try {
            repository.addJourney("P1", "J-foreign", "jornada", "INICIO>FIM", "falha", "REQ-200");
            fail("foreign requirement accepted");
        } catch (IllegalArgumentException expected) { }
        try {
            repository.addJourney("P1", "J-missing", "jornada", "INICIO>FIM", "falha", "REQ-404");
            fail("unknown requirement accepted");
        } catch (IllegalArgumentException expected) { }
        assertTrue(repository.journeys("P1").isEmpty());
    }

    @Test public void regenerationPreservesStableAcceptedIdsAndDetectsDuplicates() {
        repository.saveHypothesis("P1", "dor", "equipe", "reduzir retrabalho", "teste manual", "tempo", "< 10 min");
        repository.addScopeItem("P1", "S-001", "MUST", "capturar", "rationale", "", false);
        repository.addRequirement("P1", "REQ-001", "REQ", "S-001", "origem", "rationale", "aceite");
        PlanningPreview first = repository.preview("P1");
        repository.acceptBatch("P1", first.batchId, "HUMAN");
        repository.addScopeItem("P1", "S-002", "SHOULD", "revisar", "rationale", "", false);
        PlanningPreview regenerated = repository.preview("P1");
        assertTrue(regenerated.itemIds.contains("S-001"));
        assertTrue(regenerated.itemIds.contains("S-002"));
        try {
            repository.addRequirement("P1", "REQ-001", "REQ", "S-001", "origem", "rationale", "aceite");
            fail("duplicate requirement accepted");
        } catch (RuntimeException expected) { }
    }

    @Test public void mustCannotDependSilentlyOnItemOutsideCutAndNonGoalsAreTracked() {
        repository.addScopeItem("P1", "NG-001", "NON_GOAL", "Colaboração multiusuário", "Fora do escopo do MVP", "", true);
        repository.addScopeItem("P1", "S-010", "MUST", "Item crítico", "justificativa", "S-011", false);
        repository.addScopeItem("P1", "S-011", "SHOULD", "Item posterior", "justificativa", "", false);
        repository.addRequirement("P1", "REQ-010", "REQ", "S-010", "origem", "rationale", "criterio");
        PlanningPreview preview = repository.preview("P1");
        assertTrue(preview.coverageErrors.stream().anyMatch(e -> e.contains("must_depends_outside_cut")));
        assertTrue(repository.scopeItems("P1").stream().anyMatch(i -> i.isNonGoal && "NG-001".equals(i.itemId)));
    }

    @Test public void endToEndFlowStepsArePersistedAndOrdered() {
        repository.addFlowStep("P1", 2, "PROCESSING", "Interpretação", "Estrutura sugerida", "Timeout", "Retoma offline");
        repository.addFlowStep("P1", 1, "INITIAL", "Input inicial", "Rascunho criado", "Sem permissão", "Avisa");
        List<FlowStepEntity> steps = repository.flowSteps("P1");
        assertEquals(2, steps.size());
        assertEquals(1, steps.get(0).stepOrder);
        assertEquals("INITIAL", steps.get(0).stateName);
        assertEquals(2, steps.get(1).stepOrder);
        assertEquals("PROCESSING", steps.get(1).stateName);
    }

    @Test public void requirementValidationRejectsInvalidPatternAndEmptyAcceptance() {
        try {
            repository.addRequirement("P1", "INVALID-001", "REQ", "S-001", "origem", "rationale", "aceite");
            fail("invalid id pattern accepted");
        } catch (IllegalArgumentException expected) { }
        try {
            repository.addRequirement("P1", "REQ-099", "REQ", "S-001", "origem", "rationale", "");
            fail("empty acceptance accepted");
        } catch (IllegalArgumentException expected) { }
    }

    @Test public void nonHumanActorsAreRejectedByBatchAcceptance() {
        repository.addScopeItem("P1", "S-020", "MUST", "titulo unico", "rationale", "", false);
        PlanningPreview prev = repository.preview("P1");
        for (String actor : new String[]{"AI", "SYSTEM", "BOT", "AUTOMATION", "AI_AGENT", "UNKNOWN"}) {
            try {
                repository.acceptBatch("P1", prev.batchId, actor);
                fail("actor " + actor + " accepted batch");
            } catch (IllegalArgumentException | IllegalStateException expected) { }
        }
        assertEquals("PROPOSED", repository.batch("P1", prev.batchId).status);
    }
}
