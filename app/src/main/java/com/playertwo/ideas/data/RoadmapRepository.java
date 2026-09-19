package com.playertwo.ideas.data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/** Deterministic, offline roadmap and readiness operations for F08. */
public final class RoadmapRepository {
    private static final Set<String> TRACKS = new HashSet<>(java.util.Arrays.asList("MVP", "POST_MVP"));
    private static final Set<String> PRIORITIES = new HashSet<>(java.util.Arrays.asList("MUST", "SHOULD", "COULD", "LATER", "REJECTED"));
    private static final Pattern STABLE_ID = Pattern.compile("^[A-Z][A-Z0-9_]*(?:-[A-Z0-9_]+)*$");
    private final IdeaDatabase database;

    public RoadmapRepository(IdeaDatabase database) { if (database == null) throw new IllegalArgumentException("database required"); this.database = database; }

    public void addPhase(String projectId, String phaseId, String title, String objective, String track, int priority) {
        String p = required(projectId, "projectId"), id = stableId(phaseId, "phaseId");
        String t = required(title, "title"), o = required(objective, "objective"), tr = normalizedTrack(track);
        database.runInTransaction(() -> { requireProject(p); if (database.phases().find(p, id) != null) throw new IllegalArgumentException("duplicate phase id: " + id); database.phases().insert(new PhaseEntity(p, id, t, o, tr, priority, 1, "PROPOSED", "manual", System.currentTimeMillis())); });
    }

    public void addItem(String projectId, String itemId, String phaseId, String title, String objective,
                        String delivery, String verify, String track, String priority, String dependencies, int orderIndex) {
        String p = required(projectId, "projectId"), id = stableId(itemId, "itemId"), ph = stableId(phaseId, "phaseId");
        String tr = normalizedTrack(track), pr = normalizedPriority(priority);
        required(title, "title"); required(objective, "objective"); required(delivery, "delivery"); required(verify, "verify");
        String deps = dependencies == null ? "" : dependencies.trim();
        if (deps.contains(":")) throw new IllegalArgumentException("foreign dependency not allowed: " + deps);
        database.runInTransaction(() -> { requireProject(p); if (database.roadmapItems().find(p, id) != null) throw new IllegalArgumentException("duplicate roadmap item id: " + id); database.roadmapItems().insert(new RoadmapItemEntity(p, id, ph, title.trim(), objective.trim(), delivery.trim(), verify.trim(), tr, pr, deps, orderIndex, 1, "PROPOSED", "manual", System.currentTimeMillis())); });
    }

    public List<PhaseEntity> phases(String projectId) { return database.phases().forProject(required(projectId, "projectId")); }
    public List<RoadmapItemEntity> items(String projectId) { return database.roadmapItems().forProject(required(projectId, "projectId")); }

    public void updatePhasePriority(String projectId, String phaseId, int priority) {
        String p = required(projectId, "projectId"), id = required(phaseId, "phaseId");
        database.runInTransaction(() -> {
            PhaseEntity phase = database.phases().find(p, id);
            if (phase == null) throw new IllegalArgumentException("phase not found: " + id);
            database.phases().updatePriority(p, id, priority, System.currentTimeMillis());
        });
    }

    public void updateItemPriority(String projectId, String itemId, String priority) {
        String p = required(projectId, "projectId"), id = required(itemId, "itemId"), pr = normalizedPriority(priority);
        database.runInTransaction(() -> {
            if (database.roadmapItems().find(p, id) == null) throw new IllegalArgumentException("item not found: " + id);
            database.roadmapItems().updatePriority(p, id, pr, System.currentTimeMillis());
        });
    }

    public void reorder(String projectId, List<String> orderedItemIds) {
        String p = required(projectId, "projectId");
        List<RoadmapItemEntity> current = items(p);
        if (orderedItemIds == null || orderedItemIds.size() != current.size()) throw new IllegalArgumentException("order must contain every item exactly once");
        Set<String> expected = new HashSet<>(); for (RoadmapItemEntity item : current) expected.add(item.itemId);
        Set<String> supplied = new HashSet<>(orderedItemIds);
        if (supplied.size() != orderedItemIds.size() || !expected.equals(supplied)) throw new IllegalArgumentException("order references unknown or duplicate item");
        Map<String, Integer> positions = new HashMap<>(); for (int index = 0; index < orderedItemIds.size(); index++) positions.put(orderedItemIds.get(index), index);
        for (RoadmapItemEntity item : current) for (String dependency : split(item.dependencies)) {
            Integer dependencyPosition = positions.get(dependency);
            if (dependencyPosition == null) throw new IllegalArgumentException("order references missing dependency: " + item.itemId + "->" + dependency);
            if (dependencyPosition > positions.get(item.itemId)) throw new IllegalArgumentException("dependency order invalid: " + item.itemId + "->" + dependency);
        }
        database.runInTransaction(() -> { int index = 0; for (String id : orderedItemIds) database.roadmapItems().updateOrder(p, id, index++, System.currentTimeMillis()); });
    }

    public ReadinessReport readiness(String projectId) {
        String p = required(projectId, "projectId"); requireProject(p);
        List<PhaseEntity> phases = database.phases().forProject(p); List<RoadmapItemEntity> items = items(p);
        Map<String, PhaseEntity> phaseMap = new HashMap<>(); for (PhaseEntity phase : phases) phaseMap.put(phase.phaseId, phase);
        Map<String, RoadmapItemEntity> itemMap = new HashMap<>(); for (RoadmapItemEntity item : items) itemMap.put(item.itemId, item);
        List<String> errors = new ArrayList<>();
        for (RoadmapItemEntity item : items) {
            PhaseEntity phase = phaseMap.get(item.phaseId);
            if (phase == null) errors.add("item.orphan_phase:" + item.itemId + "@phaseId=" + item.phaseId);
            if (item.delivery.trim().isEmpty()) errors.add("item.missing_delivery:" + item.itemId);
            if (item.verify.trim().isEmpty()) errors.add("item.missing_verify:" + item.itemId);
            if (item.revision <= 0 || (phase != null && item.revision < phase.revision)) errors.add("item.old_revision:" + item.itemId);
            for (String dep : split(item.dependencies)) {
                RoadmapItemEntity dependency = itemMap.get(dep);
                if (dependency == null) errors.add("item.dependency_missing:" + item.itemId + "->" + dep);
                else if ("MVP".equals(item.track) && "POST_MVP".equals(dependency.track)) errors.add("item.mvp_depends_post_mvp:" + item.itemId + "->" + dep);
            }
        }
        errors.addAll(cycleErrors(itemMap));
        Set<String> roadmapIds = itemMap.keySet();
        for (ScopeItemEntity scope : database.scopeItems().forProject(p)) if ("MUST".equals(scope.priority) && !roadmapIds.contains(scope.itemId)) errors.add("coverage.missing_must:" + scope.itemId);
        List<String> pending = new ArrayList<>(); pending.add("G08_NOT_RUN");
        List<String> actionable = new ArrayList<>(); actionable.add("run G08 after independent F08 audit");
        boolean ready = errors.isEmpty() && pending.isEmpty();
        String status = errors.isEmpty() ? "PENDING" : "BLOCKED";
        return new ReadinessReport(p, ready, status, errors, pending, actionable);
    }

    public ReadinessSnapshotEntity persistReadiness(String projectId) {
        ReadinessReport report = readiness(projectId); long now = System.currentTimeMillis(); String snapshot = "READINESS-" + now;
        ReadinessSnapshotEntity entity = new ReadinessSnapshotEntity(report.projectId, snapshot, report.overallStatus, join(report.pendingDimensions), join(report.actionablePending), now);
        database.readiness().insert(entity); return entity;
    }

    private List<String> cycleErrors(Map<String, RoadmapItemEntity> items) {
        List<String> errors = new ArrayList<>(); Map<String, Integer> colors = new HashMap<>();
        for (String id : items.keySet()) if (colors.getOrDefault(id, 0) == 0 && hasCycle(id, items, colors)) errors.add("item.dependency_cycle:" + id);
        return errors;
    }
    private boolean hasCycle(String id, Map<String, RoadmapItemEntity> items, Map<String, Integer> colors) {
        colors.put(id, 1); for (String dep : split(items.get(id).dependencies)) if (items.containsKey(dep) && (colors.getOrDefault(dep, 0) == 1 || (colors.getOrDefault(dep, 0) == 0 && hasCycle(dep, items, colors)))) return true; colors.put(id, 2); return false;
    }
    private List<String> split(String value) { if (value == null || value.trim().isEmpty()) return Collections.emptyList(); List<String> out = new ArrayList<>(); for (String v : value.split(",")) if (!v.trim().isEmpty()) out.add(v.trim()); return out; }
    private String join(List<String> values) { return String.join("|", values); }
    private String normalizedTrack(String value) { String v = required(value, "track").toUpperCase(Locale.ROOT); if (!TRACKS.contains(v)) throw new IllegalArgumentException("invalid track: " + value); return v; }
    private String normalizedPriority(String value) { String v = required(value, "priority").toUpperCase(Locale.ROOT); if (!PRIORITIES.contains(v)) throw new IllegalArgumentException("invalid priority: " + value); return v; }
    private String stableId(String value, String name) { String v = required(value, name).toUpperCase(Locale.ROOT); if (!STABLE_ID.matcher(v).matches()) throw new IllegalArgumentException("invalid " + name + ": " + value); return v; }
    private void requireProject(String projectId) { if (database.projects().find(projectId) == null) throw new IllegalArgumentException("unknown projectId: " + projectId); }
    private String required(String value, String name) { if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(name + " required"); return value.trim(); }
}
