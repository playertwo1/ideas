package com.playertwo.ideas.domain.ai;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Structured result; authority, gates and project IDs are never assigned by the provider. */
public final class AiResult {
    public enum Outcome { SUCCESS, CANCELLED, FAILED }
    private final Outcome outcome;
    private final Map<String, String> content;
    private final Map<String, String> references;
    private final List<String> hypotheses;
    private final String executionId;
    private final String failureCode;
    private final long usageCostMicros;

    private AiResult(Outcome outcome, Map<String, String> content, Map<String, String> references,
                     List<String> hypotheses, String executionId, String failureCode, long usageCostMicros) {
        if (usageCostMicros < 0) throw new IllegalArgumentException("usageCostMicros must be non-negative");
        this.outcome = outcome;
        this.content = Collections.unmodifiableMap(content);
        this.references = Collections.unmodifiableMap(references);
        this.hypotheses = Collections.unmodifiableList(hypotheses);
        this.executionId = executionId;
        this.failureCode = failureCode;
        this.usageCostMicros = usageCostMicros;
    }

    public static AiResult success(Map<String, String> content, Map<String, String> references,
                            List<String> hypotheses, String executionId) {
        return success(content, references, hypotheses, executionId, 0);
    }

    public static AiResult success(Map<String, String> content, Map<String, String> references,
                            List<String> hypotheses, String executionId, long usageCostMicros) {
        return new AiResult(Outcome.SUCCESS, content, references, hypotheses, executionId, null, usageCostMicros);
    }

    public static AiResult cancelled(String executionId) {
        return new AiResult(Outcome.CANCELLED, Collections.emptyMap(), Collections.emptyMap(),
            Collections.emptyList(), executionId, "CANCELLED", 0);
    }

    public static AiResult failed(String executionId, String failureCode) {
        if (failureCode == null || failureCode.trim().isEmpty()) throw new IllegalArgumentException("failureCode required");
        return new AiResult(Outcome.FAILED, Collections.emptyMap(), Collections.emptyMap(),
            Collections.emptyList(), executionId, failureCode, 0);
    }

    public Outcome outcome() { return outcome; }
    public Map<String, String> content() { return content; }
    public Map<String, String> references() { return references; }
    public List<String> hypotheses() { return hypotheses; }
    public String executionId() { return executionId; }
    public String failureCode() { return failureCode; }
    public long usageCostMicros() { return usageCostMicros; }

    @Override public boolean equals(Object other) {
        if (!(other instanceof AiResult)) return false;
        AiResult that = (AiResult) other;
        return outcome == that.outcome && Objects.equals(content, that.content)
            && Objects.equals(references, that.references) && Objects.equals(hypotheses, that.hypotheses)
            && Objects.equals(executionId, that.executionId) && Objects.equals(failureCode, that.failureCode)
            && usageCostMicros == that.usageCostMicros;
    }
    @Override public int hashCode() { return Objects.hash(outcome, content, references, hypotheses, executionId, failureCode, usageCostMicros); }
}
