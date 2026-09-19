package com.playertwo.ideas.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Canonical, offline Markdown rendering for one accepted project snapshot. */
public final class MarkdownRenderer {
    private MarkdownRenderer() { }

    public static String render(ProjectEntity project, ProjectIntakeEntity snapshot,
                                List<PhaseEntity> phases, List<RoadmapItemEntity> items) {
        if (project == null || snapshot == null) throw new IllegalArgumentException("snapshot required");
        if (!snapshot.interpretationAccepted) throw new IllegalArgumentException("snapshot must be accepted");
        if (!project.projectId.equals(snapshot.projectId)) throw new IllegalArgumentException("projectId mismatch");
        List<PhaseEntity> safePhases = copyPhases(project.projectId, phases);
        List<RoadmapItemEntity> safeItems = copyItems(project.projectId, items);
        StringBuilder output = new StringBuilder();
        output.append("# Idea Snapshot\n\n");
        line(output, "projectId", project.projectId);
        line(output, "projectTitle", project.title);
        line(output, "projectStatus", project.status);
        line(output, "snapshotStatus", "ACCEPTED");
        line(output, "originalHash", snapshot.originalHash);
        output.append("\n");
        block(output, "title", snapshot.originalTitle);
        block(output, "originalIdea", snapshot.originalIdea);
        block(output, "originalLimits", snapshot.originalLimits);
        block(output, "interpretation", snapshot.interpretation);
        line(output, "projectType", snapshot.projectType);
        line(output, "restrictions", snapshot.restrictions);
        line(output, "depthMode", snapshot.depthMode);
        line(output, "suggestionReason", snapshot.suggestionReason);
        output.append("\n## Roadmap\n\n");
        for (PhaseEntity phase : safePhases) {
            output.append("### Phase ").append(escapeInline(phase.phaseId)).append("\n\n");
            line(output, "title", phase.title);
            line(output, "objective", phase.objective);
            line(output, "track", phase.track);
            line(output, "priority", Integer.toString(phase.priority));
            line(output, "revision", Integer.toString(phase.revision));
            line(output, "status", phase.status);
            line(output, "sourceRef", phase.sourceRef);
            output.append("\n");
            for (RoadmapItemEntity item : safeItems) {
                if (!phase.phaseId.equals(item.phaseId)) continue;
                output.append("#### Item ").append(escapeInline(item.itemId)).append("\n\n");
                line(output, "phaseId", item.phaseId);
                line(output, "title", item.title);
                line(output, "objective", item.objective);
                line(output, "delivery", item.delivery);
                line(output, "verify", item.verify);
                line(output, "track", item.track);
                line(output, "priority", item.priority);
                line(output, "dependencies", item.dependencies);
                line(output, "orderIndex", Integer.toString(item.orderIndex));
                line(output, "revision", Integer.toString(item.revision));
                line(output, "status", item.status);
                line(output, "sourceRef", item.sourceRef);
                output.append("\n");
            }
        }
        return output.toString();
    }

    private static List<PhaseEntity> copyPhases(String projectId, List<PhaseEntity> phases) {
        if (phases == null) throw new IllegalArgumentException("phases required");
        List<PhaseEntity> copy = new ArrayList<>();
        for (PhaseEntity phase : phases) {
            if (phase == null || !projectId.equals(phase.projectId)) throw new IllegalArgumentException("phase projectId mismatch");
            copy.add(phase);
        }
        Collections.sort(copy, Comparator.comparingInt((PhaseEntity value) -> value.priority)
            .thenComparing(value -> value.phaseId));
        return copy;
    }

    private static List<RoadmapItemEntity> copyItems(String projectId, List<RoadmapItemEntity> items) {
        if (items == null) throw new IllegalArgumentException("items required");
        List<RoadmapItemEntity> copy = new ArrayList<>();
        for (RoadmapItemEntity item : items) {
            if (item == null || !projectId.equals(item.projectId)) throw new IllegalArgumentException("item projectId mismatch");
            copy.add(item);
        }
        Collections.sort(copy, Comparator.comparingInt((RoadmapItemEntity value) -> value.orderIndex)
            .thenComparing(value -> value.itemId));
        return copy;
    }

    private static void line(StringBuilder output, String key, String value) {
        output.append("- ").append(key).append(": ").append(escapeInline(value)).append("\n");
    }

    private static void block(StringBuilder output, String key, String value) {
        String text = normalize(value);
        int fenceLength = 3;
        String fence = repeat('~', fenceLength);
        while (text.contains(fence)) fence = repeat('~', ++fenceLength);
        output.append("### ").append(key).append("\n\n")
            .append(fence).append("\n").append(text).append("\n")
            .append(fence).append("\n\n");
    }

    private static String normalize(String value) { return value == null ? "" : value.replace("\r\n", "\n").replace('\r', '\n'); }

    private static String escapeInline(String value) {
        return normalize(value).replace("\\", "\\\\").replace("\n", "\\n");
    }

    private static String repeat(char value, int count) {
        StringBuilder result = new StringBuilder(count);
        for (int index = 0; index < count; index++) result.append(value);
        return result.toString();
    }
}
