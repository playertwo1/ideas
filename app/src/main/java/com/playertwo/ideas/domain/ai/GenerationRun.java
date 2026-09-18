package com.playertwo.ideas.domain.ai;

public final class GenerationRun {
    private final String runId;
    private final String projectId;
    private final long inputRevision;
    private final String promptId;
    private final String promptVersion;
    private final String providerId;
    private final GenerationStatus status;
    private final AiResult result;
    private final String failureCode;
    private final int requestedTokens;
    private final long usageCostMicros;

    public GenerationRun(String runId, String projectId, long inputRevision, String promptId,
                         String promptVersion, String providerId, GenerationStatus status,
                         AiResult result, String failureCode) {
        this(runId, projectId, inputRevision, promptId, promptVersion, providerId, status, result, failureCode, 0, 0);
    }
    public GenerationRun(String runId, String projectId, long inputRevision, String promptId,
                         String promptVersion, String providerId, GenerationStatus status,
                         AiResult result, String failureCode, int requestedTokens, long usageCostMicros) {
        this.runId = runId; this.projectId = projectId; this.inputRevision = inputRevision;
        this.promptId = promptId; this.promptVersion = promptVersion; this.providerId = providerId;
        this.status = status; this.result = result; this.failureCode = failureCode;
        this.requestedTokens = requestedTokens; this.usageCostMicros = usageCostMicros;
    }
    public String runId() { return runId; }
    public String projectId() { return projectId; }
    public long inputRevision() { return inputRevision; }
    public String promptId() { return promptId; }
    public String promptVersion() { return promptVersion; }
    public String providerId() { return providerId; }
    public GenerationStatus status() { return status; }
    public AiResult result() { return result; }
    public String failureCode() { return failureCode; }
    public int requestedTokens() { return requestedTokens; }
    public long usageCostMicros() { return usageCostMicros; }
    public boolean terminal() { return status != GenerationStatus.RUNNING; }
}
