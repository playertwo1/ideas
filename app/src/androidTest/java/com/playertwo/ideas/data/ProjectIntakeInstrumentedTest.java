package com.playertwo.ideas.data;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.room.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ProjectIntakeInstrumentedTest {
    private IdeaDatabase db;
    private ProjectIntakeRepository repository;

    @Before public void setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), IdeaDatabase.class)
            .allowMainThreadQueries().build();
        repository = new ProjectIntakeRepository(db);
    }

    @After public void tearDown() { db.close(); }

    @Test public void createPreservesAccentsMultilineLimitsAndOriginalHash() {
        ProjectIntakeEntity intake = repository.create("P1", "Título café", "linha 1\nlinha 2", "Sem Firebase\nOffline");
        assertEquals("Título café", intake.originalTitle);
        assertEquals("linha 1\nlinha 2", intake.originalIdea);
        assertEquals("Sem Firebase\nOffline", intake.originalLimits);
        assertEquals(ProjectIntakeRepository.originalHash("Título café", "linha 1\nlinha 2", "Sem Firebase\nOffline"), intake.originalHash);
        assertEquals("linha 1\nlinha 2", db.drafts().find("P1").content);
    }

    @Test public void interpretationIsEditableWithoutChangingOriginalSnapshot() {
        ProjectIntakeEntity before = repository.create("P1", "Original", "ideia original", "limite");
        repository.updateSuggestion("P1", "interpretação editada", "APP", "sem rede", "STANDARD", "motivo visível");
        ProjectIntakeEntity after = repository.find("P1");
        assertEquals(before.originalHash, after.originalHash);
        assertEquals(before.originalIdea, after.originalIdea);
        assertEquals("interpretação editada", after.interpretation);
        assertEquals("motivo visível", after.suggestionReason);
        assertFalse(after.interpretationAccepted);
    }

    @Test public void userCanAcceptOrRejectSuggestionAndCorrectMode() {
        repository.create("P1", "Original", "ideia", "limite");
        repository.updateSuggestion("P1", "sugestão", "APP", "sem upload", "DEEP", "porque");
        repository.acceptSuggestion("P1");
        assertTrue(repository.find("P1").interpretationAccepted);
        repository.updateSuggestion("P1", "correção", "SCRIPT", "offline", "STANDARD", "usuário corrigiu");
        assertFalse(repository.find("P1").interpretationAccepted);
        repository.rejectSuggestion("P1");
        ProjectIntakeEntity rejected = repository.find("P1");
        assertNull(rejected.interpretation);
        assertNull(rejected.projectType);
        assertNull(rejected.restrictions);
        assertNull(rejected.depthMode);
        assertNull(rejected.suggestionReason);
        assertFalse(rejected.interpretationAccepted);
    }

    @Test public void progressAndNextActionArePersistedPerProject() {
        repository.create("P1", "Um", "ideia um", "limite");
        repository.create("P2", "Dois", "ideia dois", "limite");
        repository.updateProgress("P1", 40, "Revisar interpretação");
        assertEquals(40, repository.find("P1").progressPercent);
        assertEquals("Revisar interpretação", repository.find("P1").nextAction);
        assertEquals(40, repository.find("P2").progressPercent);
        assertEquals("Gerar interpretação", repository.find("P2").nextAction);
    }

    @Test public void progressIsDerivedFromPersistedSnapshot() {
        repository.create("P1", "Um", "ideia", "limite");
        assertEquals(40, repository.find("P1").progressPercent);
        repository.updateSuggestion("P1", "sugestão", "APP", "offline", "STANDARD", "motivo");
        assertEquals(60, repository.find("P1").progressPercent);
        assertEquals("Aceitar ou rejeitar sugestão", repository.find("P1").nextAction);
        repository.acceptSuggestion("P1");
        assertEquals(80, repository.find("P1").progressPercent);
        repository.rejectSuggestion("P1");
        assertEquals(40, repository.find("P1").progressPercent);
        assertEquals("Gerar interpretação", repository.find("P1").nextAction);
    }

    @Test public void legacyProjectWithoutDraftDoesNotFreezeEmptyOriginalSnapshot() {
        db.projects().insert(new ProjectEntity("LEGACY", "Rascunho", "ACTIVE", 1));
        repository.ensureFromLegacy("LEGACY");
        assertNull(repository.find("LEGACY"));
    }

    @Test public void providerSuggestionIsStoredAsEditableUnacceptedInterpretation() {
        repository.create("P1", "Título", "ideia original", "sem rede");
        new ProjectInterpretationService(repository).suggest("P1", new com.playertwo.ideas.domain.ai.FakeAiProvider(),
            com.playertwo.ideas.domain.ai.CancellationToken.never());
        ProjectIntakeEntity intake = repository.find("P1");
        assertNotNull(intake.interpretation);
        assertFalse(intake.interpretationAccepted);
        assertNotNull(intake.suggestionReason);
    }
}
