package com.playertwo.ideas.domain.ai;

public final class GenerationBudget {
    private final int maxTokens;
    private final int maxContextCharacters;
    private final long maxCostMicros;
    public GenerationBudget(int maxTokens, int maxContextCharacters, long maxCostMicros) {
        if (maxTokens <= 0 || maxContextCharacters <= 0 || maxCostMicros < 0) throw new IllegalArgumentException("invalid budget");
        this.maxTokens = maxTokens; this.maxContextCharacters = maxContextCharacters; this.maxCostMicros = maxCostMicros;
    }
    public void validate(AiRequest request) {
        if (request == null) throw new IllegalArgumentException("request required");
        if (request.maxTokens() > maxTokens) throw new IllegalArgumentException("token budget exceeded");
        int chars = 0;
        for (java.util.Map.Entry<String, String> entry : request.context().entrySet()) chars += entry.getKey().length() + entry.getValue().length();
        for (java.util.Map.Entry<String, String> entry : request.decisions().entrySet()) chars += entry.getKey().length() + entry.getValue().length();
        if (chars > maxContextCharacters) throw new IllegalArgumentException("minimum context budget exceeded");
    }
    public void validateCost(long usageCostMicros) {
        if (usageCostMicros < 0) throw new IllegalArgumentException("usage cost must be non-negative");
        if (usageCostMicros > maxCostMicros) throw new IllegalArgumentException("cost budget exceeded");
    }
    public int maxTokens() { return maxTokens; }
    public long maxCostMicros() { return maxCostMicros; }
}
