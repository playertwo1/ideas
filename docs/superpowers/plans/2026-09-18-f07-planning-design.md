# F07 MVP Planning Implementation Plan

> **For agentic workers:** Implement this plan task-by-task with test-first checks. Do not start F08 or register G07.

**Goal:** Add an offline, provider-neutral planning core covering F07.01–F07.06 with stable IDs, traceability, editable previews and human-only batch acceptance.

**Architecture:** Add a small `planning` data layer backed by Room. Each record is scoped by `projectId`; repository operations are deterministic and transactional. Preview/regeneration never mutates accepted records; only an explicit human action accepts a proposal.

**Tech Stack:** Java, Room, Android instrumented tests, existing `IdeaDatabase`.

**Spec:** `ROADMAP.md` F07.01–F07.06.

## Global Constraints

- Offline by default; no network, provider, synchronization or secrets.
- Preserve F00–F06 and D01–D09.
- `G06 = PASS`; `G07 = NOT_RUN`; F08 remains unstarted.
- All planning records carry `projectId`; IDs are stable and deterministic.
- No automatic lock or approval.

### Task 1: Define planning records and schema

**Files:**
- Create: `app/src/main/java/com/playertwo/ideas/data/HypothesisEntity.java`
- Create: `app/src/main/java/com/playertwo/ideas/data/ScopeItemEntity.java`
- Create: `app/src/main/java/com/playertwo/ideas/data/RequirementEntity.java`
- Create: `app/src/main/java/com/playertwo/ideas/data/JourneyEntity.java`
- Create: matching Room DAOs and schema export
- Modify: `IdeaDatabase.java` with version 5 migration
- Test: `F07PlanningInstrumentedTest.java`

- [ ] Add IDs, projectId, rationale, status and acceptance fields with closed status vocabularies.
- [ ] Add migration tables and foreign keys to `projects`.
- [ ] Write a fixture test proving two projects cannot read or mutate each other.
- [ ] Run the new test and confirm it fails before repository behavior exists.

### Task 2: Implement F07.01 hypothesis and F07.02 scope

**Files:**
- Create/modify: `PlanningRepository.java`
- Test: `F07PlanningInstrumentedTest.java`

- [ ] Implement create/update hypothesis with `NOT_TESTED` default for unmeasured data.
- [ ] Implement scope items with MUST/SHOULD/COULD/LATER/REJECTED, rationale, dependencies and non-goals.
- [ ] Reject a MUST without a linked requirement and dependencies outside the project scope.
- [ ] Add tests for valid records, NOT_TESTED and orphan rejection.

### Task 3: Implement F07.03–F07.05 flow and traceability

**Files:**
- Modify: `PlanningRepository.java`, journey/requirement DAOs
- Test: `F07PlanningInstrumentedTest.java`

- [ ] Add minimum flow states and non-goals.
- [ ] Add REQ/NFR records with stable IDs, origin, rationale and editable acceptance.
- [ ] Add journeys and material failure paths linked to requirements.
- [ ] Test that every MUST is linked to a requirement and every material failure has a linked path.

### Task 4: Implement F07.06 preview, regeneration and human acceptance

**Files:**
- Create: `PlanningBatchEntity.java` and DAO if required
- Modify: `PlanningRepository.java`
- Test: `F07PlanningInstrumentedTest.java`

- [ ] Generate an editable proposal preview with deterministic coverage/orphan/duplicate checks.
- [ ] Regenerate without changing IDs of accepted items.
- [ ] Keep proposals unaccepted until `acceptBatch(projectId, batchId, HUMAN)` is called.
- [ ] Reject AI/system/unknown acceptance actors and test that rejected operations do not mutate accepted state.

### Task 5: Evidence and phase state

**Files:**
- Create: `F07_EVIDENCE.json`
- Modify: `PROJECT_STATE.md`, `PHASE_CURRENT.md`

- [ ] Record scenarios, commands, test counts, artifact SHA-256 digests and limitations.
- [ ] Mark F07 implemented/awaiting audit; keep G07 NOT_RUN and F08 unstarted.

### Task 6: Full verification

- [ ] Run `gradlew.bat assembleDebug`, `test`, `lint`, `connectedDebugAndroidTest`.
- [ ] Run `python scripts/validate_standard.py --self-check` and `python -m unittest discover -s scripts -q`.
- [ ] Run `git diff --check` and verify evidence digests against committed blobs.
- [ ] Commit the implementation and report the SHA.
