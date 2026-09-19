package com.playertwo.ideas.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public final class PlanningRepository {
    private static final Pattern REQ_ID_PATTERN = Pattern.compile("^(REQ|NFR)-[A-Z0-9_-]+$");
    private static final Set<String> PRIORITIES = new HashSet<>(java.util.Arrays.asList("MUST", "SHOULD", "COULD", "LATER", "REJECTED", "NON_GOAL"));
    private final IdeaDatabase database;

    public PlanningRepository(IdeaDatabase database) {
        if (database == null) throw new IllegalArgumentException("database required");
        this.database = database;
    }

    public void saveHypothesis(String projectId, String problem, String audience, String statement,
                               String minimumTest, String metric, String threshold) {
        saveHypothesis(projectId, problem, audience, statement, minimumTest, metric, threshold, "NOT_TESTED");
    }

    public void saveHypothesis(String projectId, String problem, String audience, String statement,
                               String minimumTest, String metric, String threshold, String evidenceStatus) {
        String id = required(projectId, "projectId");
        required(problem, "problem");
        required(audience, "audience");
        required(statement, "statement");
        required(minimumTest, "minimumTest");
        required(metric, "metric");
        required(threshold, "threshold");
        String status = (evidenceStatus == null || evidenceStatus.trim().isEmpty()) ? "NOT_TESTED" : evidenceStatus.trim();
        long now = System.currentTimeMillis();
        database.runInTransaction(() -> {
            requireProject(id);
            database.hypotheses().upsert(new HypothesisEntity(id, problem, audience, statement, minimumTest,
                    metric, threshold, status, "PROPOSED", now));
        });
    }

    public HypothesisEntity hypothesis(String projectId) {
        return database.hypotheses().find(required(projectId, "projectId"));
    }

    public void addScopeItem(String projectId, String itemId, String priority, String title,
                             String rationale, String dependencies, boolean isNonGoal) {
        String id = required(projectId, "projectId");
        required(itemId, "itemId");
        required(priority, "priority");
        String normalizedPriority = priority.trim().toUpperCase(Locale.ROOT);
        if (!PRIORITIES.contains(normalizedPriority)) {
            throw new IllegalArgumentException("invalid scope priority: " + priority);
        }
        required(title, "title");
        required(rationale, "rationale");
        String deps = dependencies == null ? "" : dependencies.trim();
        if (!deps.isEmpty()) {
            for (String dep : deps.split(",")) {
                String trimmed = dep.trim();
                if (trimmed.contains(":")) {
                    throw new IllegalArgumentException("foreign dependency not allowed: " + trimmed);
                }
            }
        }
        long now = System.currentTimeMillis();
        database.runInTransaction(() -> {
            requireProject(id);
            if (database.scopeItems().find(id, itemId) != null) {
                throw new IllegalArgumentException("item already exists: " + itemId);
            }
            database.scopeItems().insert(new ScopeItemEntity(id, itemId, normalizedPriority, title, rationale, deps, isNonGoal, "PROPOSED", now));
        });
    }

    public List<ScopeItemEntity> scopeItems(String projectId) {
        return database.scopeItems().forProject(required(projectId, "projectId"));
    }

    public void addRequirement(String projectId, String reqId, String type, String scopeItemId,
                               String origin, String rationale, String acceptance) {
        String id = required(projectId, "projectId");
        required(reqId, "reqId");
        if (!REQ_ID_PATTERN.matcher(reqId).matches()) {
            throw new IllegalArgumentException("invalid requirement id pattern: " + reqId);
        }
        required(type, "type");
        String normalizedType = type.trim().toUpperCase(Locale.ROOT);
        if (!("REQ".equals(normalizedType) || "NFR".equals(normalizedType))) {
            throw new IllegalArgumentException("invalid requirement type: " + type);
        }
        required(scopeItemId, "scopeItemId");
        required(origin, "origin");
        required(rationale, "rationale");
        required(acceptance, "acceptance");
        long now = System.currentTimeMillis();
        database.runInTransaction(() -> {
            requireProject(id);
            if (database.scopeItems().find(id, scopeItemId) == null) {
                throw new IllegalArgumentException("requirement scope item not found: " + scopeItemId);
            }
            if (database.requirements().find(id, reqId) != null) {
                throw new IllegalStateException("duplicate requirement: " + reqId);
            }
            database.requirements().insert(new RequirementEntity(id, reqId, normalizedType, scopeItemId, origin, rationale, acceptance, "PROPOSED", now));
        });
    }

    public List<RequirementEntity> requirements(String projectId) {
        return database.requirements().forProject(required(projectId, "projectId"));
    }

    public void addJourney(String projectId, String journeyId, String title, String states,
                           String materialErrors, String linkedReqId) {
        String id = required(projectId, "projectId");
        required(journeyId, "journeyId");
        required(title, "title");
        required(states, "states");
        String errors = materialErrors == null ? "" : materialErrors.trim();
        long now = System.currentTimeMillis();
        database.runInTransaction(() -> {
            requireProject(id);
            if (database.journeys().find(id, journeyId) != null) {
                throw new IllegalStateException("journey already exists: " + journeyId);
            }
            database.journeys().insert(new JourneyEntity(id, journeyId, title, states, errors, linkedReqId, "PROPOSED", now));
        });
    }

    public List<JourneyEntity> journeys(String projectId) {
        return database.journeys().forProject(required(projectId, "projectId"));
    }

    public void addFlowStep(String projectId, int stepOrder, String stateName, String action,
                            String expectedResult, String failureCondition, String failureHandling) {
        String id = required(projectId, "projectId");
        required(stateName, "stateName");
        required(action, "action");
        required(expectedResult, "expectedResult");
        long now = System.currentTimeMillis();
        database.runInTransaction(() -> {
            requireProject(id);
            database.flowSteps().insert(new FlowStepEntity(id, stepOrder, stateName, action, expectedResult, failureCondition, failureHandling, "PROPOSED", now));
        });
    }

    public List<FlowStepEntity> flowSteps(String projectId) {
        return database.flowSteps().forProject(required(projectId, "projectId"));
    }

    public PlanningBatchEntity batch(String projectId, String batchId) {
        return database.planningBatches().find(required(projectId, "projectId"), required(batchId, "batchId"));
    }

    public PlanningPreview preview(String projectId) {
        String id = required(projectId, "projectId");
        requireProject(id);

        List<ScopeItemEntity> items = database.scopeItems().forProject(id);
        List<RequirementEntity> reqs = database.requirements().forProject(id);
        List<JourneyEntity> jrns = database.journeys().forProject(id);
        HypothesisEntity hypo = database.hypotheses().find(id);

        List<String> itemIds = new ArrayList<>();
        List<String> orphanMustIds = new ArrayList<>();
        Set<String> linkedScopeItemIds = new HashSet<>();
        Set<String> reqIdSet = new HashSet<>();
        for (RequirementEntity req : reqs) {
            reqIdSet.add(req.reqId);
            linkedScopeItemIds.add(req.scopeItemId);
        }

        Map<String, ScopeItemEntity> itemMap = new HashMap<>();
        for (ScopeItemEntity item : items) {
            itemIds.add(item.itemId);
            itemMap.put(item.itemId, item);
            if ("MUST".equalsIgnoreCase(item.priority)) {
                if (!linkedScopeItemIds.contains(item.itemId)) {
                    orphanMustIds.add(item.itemId);
                }
            }
        }

        List<String> unlinkedFailureIds = new ArrayList<>();
        for (JourneyEntity jrn : jrns) {
            if (jrn.materialErrors != null && !jrn.materialErrors.trim().isEmpty()) {
                if (jrn.linkedReqId == null || !reqIdSet.contains(jrn.linkedReqId)) {
                    unlinkedFailureIds.add(jrn.journeyId);
                }
            }
        }

        List<String> duplicateWarnings = new ArrayList<>();
        Set<String> seenTitles = new HashSet<>();
        for (ScopeItemEntity item : items) {
            String norm = item.title.trim().toLowerCase(Locale.ROOT);
            if (!seenTitles.add(norm)) {
                duplicateWarnings.add("duplicate_scope_item:" + item.itemId);
            }
        }
        Set<String> seenReqTitles = new HashSet<>();
        for (RequirementEntity req : reqs) {
            String norm = req.rationale.trim().toLowerCase(Locale.ROOT);
            if (!seenReqTitles.add(norm)) {
                duplicateWarnings.add("duplicate_requirement:" + req.reqId);
            }
        }

        List<String> coverageErrors = new ArrayList<>();
        if (hypo == null) {
            coverageErrors.add("hypothesis_missing");
        }
        for (String orphan : orphanMustIds) {
            coverageErrors.add("orphan_must:" + orphan);
        }
        for (String unlinked : unlinkedFailureIds) {
            coverageErrors.add("unlinked_failure:" + unlinked);
        }

        // Check: MUST cannot depend on items outside the cut
        for (ScopeItemEntity item : items) {
            if ("MUST".equalsIgnoreCase(item.priority) && item.dependencies != null && !item.dependencies.isEmpty()) {
                for (String depId : item.dependencies.split(",")) {
                    String trimmed = depId.trim();
                    if (!trimmed.isEmpty()) {
                        ScopeItemEntity depItem = itemMap.get(trimmed);
                        if (depItem == null || !"MUST".equalsIgnoreCase(depItem.priority)) {
                            coverageErrors.add("must_depends_outside_cut:" + item.itemId + "->" + trimmed);
                        }
                    }
                }
            }
            if (item.dependencies != null && !item.dependencies.trim().isEmpty() && !"MUST".equalsIgnoreCase(item.priority)) {
                for (String depId : item.dependencies.split(",")) {
                    String trimmed = depId.trim();
                    if (!trimmed.isEmpty() && !itemMap.containsKey(trimmed)) {
                        coverageErrors.add("dependency_outside_cut:" + item.itemId + "->" + trimmed);
                    }
                }
            }
        }

        List<PlanningBatchEntity> batches = database.planningBatches().forProject(id);
        PlanningBatchEntity openBatch = null;
        for (PlanningBatchEntity b : batches) {
            if ("PROPOSED".equals(b.status)) {
                openBatch = b;
                break;
            }
        }

        String batchId;
        if (openBatch != null) {
            batchId = openBatch.batchId;
        } else {
            int nextIndex = batches.size() + 1;
            batchId = "BATCH-" + id + "-" + nextIndex;
            PlanningBatchEntity currentBatch = new PlanningBatchEntity(id, batchId, "PROPOSED", "SYSTEM", System.currentTimeMillis());
            database.planningBatches().insert(currentBatch);
        }

        boolean isReady = coverageErrors.isEmpty() && duplicateWarnings.isEmpty();
        return new PlanningPreview(batchId, itemIds, orphanMustIds, unlinkedFailureIds, duplicateWarnings, coverageErrors, isReady);
    }

    public void acceptBatch(String projectId, String batchId, String actor) {
        String id = required(projectId, "projectId");
        required(batchId, "batchId");
        ActorType actorType = ActorType.parse(actor);
        if (actorType != ActorType.HUMAN) {
            throw new IllegalStateException("only human actor can accept batch");
        }
        database.runInTransaction(() -> {
            PlanningBatchEntity b = database.planningBatches().find(id, batchId);
            if (b == null) throw new IllegalArgumentException("batch not found: " + batchId);
            long now = System.currentTimeMillis();
            database.planningBatches().updateStatus(id, batchId, "ACCEPTED");
            database.scopeItems().acceptProposed(id, "ACCEPTED", now);
            database.requirements().acceptProposed(id, "ACCEPTED", now);
            database.journeys().acceptProposed(id, "ACCEPTED", now);
            database.flowSteps().acceptProposed(id, "ACCEPTED", now);
            database.hypotheses().updateStatus(id, "ACCEPTED", now);
        });
    }

    private void requireProject(String projectId) {
        if (database.projects().find(projectId) == null) {
            throw new IllegalArgumentException("unknown projectId: " + projectId);
        }
    }

    private String required(String value, String name) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(name + " required");
        return value.trim();
    }
}
