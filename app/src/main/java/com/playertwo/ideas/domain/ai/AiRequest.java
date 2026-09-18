package com.playertwo.ideas.domain.ai;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/** Immutable, provider-neutral input for one generation attempt. */
public final class AiRequest {
    private final String operation;
    private final String projectId;
    private final long inputRevision;
    private final String schemaVersion;
    private final Map<String, String> context;
    private final Map<String, String> decisions;
    private final int maxTokens;
    private final String requestId;

    public AiRequest(String operation, String projectId, long inputRevision, String schemaVersion,
                     Map<String, String> context, Map<String, String> decisions,
                     int maxTokens, String requestId) {
        this.operation = required(operation, "operation");
        this.projectId = required(projectId, "projectId");
        if (inputRevision < 0) throw new IllegalArgumentException("inputRevision must be non-negative");
        this.inputRevision = inputRevision;
        this.schemaVersion = required(schemaVersion, "schemaVersion");
        this.context = immutable(context, "context");
        this.decisions = immutable(decisions, "decisions");
        if (maxTokens <= 0) throw new IllegalArgumentException("maxTokens must be positive");
        this.maxTokens = maxTokens;
        this.requestId = required(requestId, "requestId");
    }

    public String operation() { return operation; }
    public String projectId() { return projectId; }
    public long inputRevision() { return inputRevision; }
    public String schemaVersion() { return schemaVersion; }
    public Map<String, String> context() { return context; }
    public Map<String, String> decisions() { return decisions; }
    public int maxTokens() { return maxTokens; }
    public String requestId() { return requestId; }

    String canonical() {
        return operation + "\n" + projectId + "\n" + inputRevision + "\n" + schemaVersion
            + "\n" + context + "\n" + decisions + "\n" + maxTokens + "\n" + requestId;
    }

    private static String required(String value, String name) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(name + " required");
        return value;
    }

    private static Map<String, String> immutable(Map<String, String> value, String name) {
        if (value == null) throw new IllegalArgumentException(name + " required");
        TreeMap<String, String> copy = new TreeMap<>();
        for (Map.Entry<String, String> entry : value.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().isEmpty() || entry.getValue() == null)
                throw new IllegalArgumentException(name + " contains invalid entry");
            copy.put(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(copy);
    }
}
