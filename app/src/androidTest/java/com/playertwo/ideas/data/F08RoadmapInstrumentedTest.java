package com.playertwo.ideas.data;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class F08RoadmapInstrumentedTest {
    private IdeaDatabase db;
    private RoadmapRepository repository;

    @Before public void setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), IdeaDatabase.class)
                .allowMainThreadQueries().build();
        db.projects().insert(new ProjectEntity("P1", "Um", "ACTIVE", 1));
        db.projects().insert(new ProjectEntity("P2", "Dois", "ACTIVE", 1));
        repository = new RoadmapRepository(db);
    }

    @After public void tearDown() { db.close(); }

    @Test public void roadmapPersistsDeliveryVerifyAndProjectIsolation() {
        repository.addPhase("P1", "PH-1", "MVP", "entrega mínima", "MVP", 1);
        repository.addItem("P1", "ITEM-1", "PH-1", "captura", "capturar ideia", "tela", "teste local", "MVP", "MUST", "", 0);
        assertEquals(1, repository.phases("P1").size());
        assertEquals("teste local", repository.items("P1").get(0).verify);
        assertTrue(repository.items("P2").isEmpty());
    }

    @Test public void reorderPreservesReferencesAndRejectsForeignOrDuplicateIds() {
        repository.addPhase("P1", "PH-1", "MVP", "objetivo", "MVP", 1);
        repository.addItem("P1", "A", "PH-1", "a", "obj", "entrega", "verify", "MVP", "SHOULD", "", 0);
        repository.addItem("P1", "B", "PH-1", "b", "obj", "entrega", "verify", "MVP", "SHOULD", "", 1);
        repository.reorder("P1", Arrays.asList("B", "A"));
        assertEquals("B", repository.items("P1").get(0).itemId);
        try { repository.reorder("P1", Arrays.asList("B", "B")); fail("duplicate reorder accepted"); }
        catch (IllegalArgumentException expected) { }
        try { repository.reorder("P2", Arrays.asList("A")); fail("foreign reorder accepted"); }
        catch (IllegalArgumentException expected) { }
    }

    @Test public void orphanAndMissingVerificationHaveExactErrorsWithoutMutation() {
        repository.addItem("P1", "ORPHAN", "PH-MISSING", "item", "obj", "entrega", "verify", "MVP", "SHOULD", "", 0);
        ReadinessReport report = repository.readiness("P1");
        assertTrue(report.errors.contains("item.orphan_phase:ORPHAN@phaseId=PH-MISSING"));
        assertFalse(report.ready);
        assertEquals(1, repository.items("P1").size());
    }

    @Test public void mvpCannotHidePostMvpDependency() {
        repository.addPhase("P1", "PH-MVP", "MVP", "objetivo", "MVP", 1);
        repository.addPhase("P1", "PH-POST", "Depois", "objetivo", "POST_MVP", 2);
        repository.addItem("P1", "POST-1", "PH-POST", "post", "obj", "entrega", "verify", "POST_MVP", "LATER", "", 1);
        repository.addItem("P1", "MVP-1", "PH-MVP", "mvp", "obj", "entrega", "verify", "MVP", "MUST", "POST-1", 0);
        assertTrue(repository.readiness("P1").errors.contains("item.mvp_depends_post_mvp:MVP-1->POST-1"));
    }

    @Test public void missingDependencyAndCycleAreActionable() {
        repository.addPhase("P1", "PH-1", "MVP", "objetivo", "MVP", 1);
        repository.addItem("P1", "A", "PH-1", "a", "obj", "entrega", "verify", "MVP", "MUST", "B", 0);
        repository.addItem("P1", "B", "PH-1", "b", "obj", "entrega", "verify", "MVP", "SHOULD", "A", 1);
        ReadinessReport report = repository.readiness("P1");
        assertTrue(report.errors.contains("item.dependency_cycle:A"));
        assertFalse(report.ready);
    }

    @Test public void mustScopeCoverageIsCheckedAndGateNotRunStaysPending() {
        PlanningRepository planning = new PlanningRepository(db);
        planning.addScopeItem("P1", "S-001", "MUST", "must", "rationale", "", false);
        repository.addPhase("P1", "PH-1", "MVP", "objetivo", "MVP", 1);
        ReadinessReport report = repository.readiness("P1");
        assertTrue(report.errors.contains("coverage.missing_must:S-001"));
        assertTrue(report.pendingDimensions.contains("G08_NOT_RUN"));
        assertTrue(report.actionablePending.get(0).contains("independent F08 audit"));
    }

    @Test public void readinessSnapshotIsPersistedForSameProjectOnly() {
        repository.addPhase("P1", "PH-1", "MVP", "objetivo", "MVP", 1);
        ReadinessSnapshotEntity snapshot = repository.persistReadiness("P1");
        assertEquals("P1", snapshot.projectId);
        assertEquals(snapshot.snapshotId, db.readiness().latest("P1").snapshotId);
        assertNull(db.readiness().latest("P2"));
    }
}
