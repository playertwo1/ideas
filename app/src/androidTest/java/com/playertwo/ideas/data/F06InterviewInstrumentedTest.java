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
public class F06InterviewInstrumentedTest {
    private IdeaDatabase db;
    private InterviewRepository repository;

    @Before public void setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), IdeaDatabase.class)
            .allowMainThreadQueries().build();
        db.projects().insert(new ProjectEntity("P1", "Projeto", "ACTIVE", 1));
        db.projects().insert(new ProjectEntity("P2", "Vizinho", "ACTIVE", 1));
        repository = new InterviewRepository(db);
    }

    @After public void tearDown() { db.close(); }

    @Test public void simpleCatalogDoesNotReceiveDeepQuestionnaire() {
        assertEquals(2, GapCatalog.forTypeAndMode("GENERAL", "STANDARD").size());
        assertEquals(4, GapCatalog.forTypeAndMode("APP", "DEEP").size());
    }

    @Test public void adaptiveRoundsDoNotRepeatAnsweredQuestions() {
        repository.initialize("P1", "APP", "DEEP", 3);
        String first = repository.nextQuestions("P1").get(0).gapId;
        repository.answer("P1", first, "validar", "USER");
        for (InterviewGapEntity gap : repository.nextQuestions("P1")) assertNotEquals(first, gap.gapId);
        assertTrue(repository.history("P1").stream().anyMatch(item -> "ANSWER".equals(item.action)));
    }

    @Test public void deferAndDefaultAreExplicitAndCriticalDeferBlocksGate() {
        repository.initialize("P1", "APP", "DEEP", 2);
        InterviewGapEntity critical = repository.nextQuestions("P1").stream()
            .filter(item -> "CRITICAL".equals(item.criticality)).findFirst().get();
        repository.defer("P1", critical.gapId, "USER");
        assertTrue(repository.session("P1").gateBlocked);
        InterviewGapEntity optional = repository.nextQuestions("P1").stream()
            .filter(item -> item.defaultValue != null).findFirst().get();
        repository.acceptDefault("P1", optional.gapId, "USER");
        assertEquals("DEFAULT", repository.allQuestions("P1").stream().filter(item -> item.gapId.equals(optional.gapId)).findFirst().get().answerSource);
    }

    @Test public void onlyHumanCanLockAndCriticalAnswersAreRequired() {
        repository.initialize("P1", "GENERAL", "STANDARD", 2);
        try { repository.lock("P1", "AI", "automatic"); fail("AI locked decision"); }
        catch (IllegalStateException expected) { }
        repository.answer("P1", "goal", "validar", "USER");
        repository.answer("P1", "audience", "eu", "USER");
        repository.refine("P1", "USER", "revisão humana");
        repository.lock("P1", "USER", "decisão explícita");
        assertEquals("LOCKED", repository.latestRevision("P1").status);
        assertEquals("LOCKED", repository.session("P1").status);
    }

    @Test public void rejectsNonHumanAndUnknownLockActors() {
        repository.initialize("P1", "GENERAL", "STANDARD", 2);
        repository.answer("P1", "goal", "validar", "USER");
        repository.answer("P1", "audience", "eu", "USER");
        for (String actor : new String[] {"AI", "SYSTEM", "BOT", "AUTOMATION", "AI_AGENT", "NOT_A_ROLE"}) {
            try {
                repository.lock("P1", actor, "tentativa não humana");
                fail("lock accepted actor " + actor);
            } catch (IllegalArgumentException | IllegalStateException expected) { }
        }
        assertEquals("ACTIVE", repository.session("P1").status);
        assertNotEquals("LOCKED", repository.latestRevision("P1").status);
        repository.lock("P1", ActorType.HUMAN, "decisão explícita");
        assertEquals("HUMAN", repository.latestRevision("P1").author);
    }

    @Test public void reopenCreatesDeltaAndInvalidatesDerivedRevisions() {
        repository.initialize("P1", "GENERAL", "STANDARD", 2);
        repository.answer("P1", "goal", "validar", "USER");
        repository.answer("P1", "audience", "eu", "USER");
        repository.lock("P1", "USER", "fechado");
        repository.reopen("P1", "goal", "USER", "mudança de escopo");
        assertEquals("PROPOSED", repository.latestRevision("P1").status);
        assertFalse(repository.latestRevision("P1").derivedValid);
        assertEquals("OPEN", repository.allQuestions("P1").stream().filter(item -> item.gapId.equals("goal")).findFirst().get().status);
        assertTrue(repository.history("P1").stream().anyMatch(item -> "REOPEN".equals(item.action)));
    }

    @Test public void roundBudgetStopsWithChoiceAndNeverApproves() {
        repository.initialize("P1", "GENERAL", "STANDARD", 1);
        repository.advanceRound("P1");
        assertEquals("EXHAUSTED", repository.session("P1").status);
        assertTrue(repository.session("P1").nextAction.contains("Escolha"));
        assertNotEquals("LOCKED", repository.latestRevision("P1").status);
    }

    @Test public void historyAndStateAreIsolatedByProjectId() {
        repository.initialize("P1", "GENERAL", "STANDARD", 2);
        repository.initialize("P2", "GENERAL", "STANDARD", 2);
        repository.answer("P1", "goal", "validar", "USER");
        assertEquals(0, repository.history("P2").stream().filter(item -> "ANSWER".equals(item.action)).count());
        assertEquals("OPEN", repository.allQuestions("P2").get(0).status);
    }
}
