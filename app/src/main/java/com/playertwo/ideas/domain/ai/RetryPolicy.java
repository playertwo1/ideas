package com.playertwo.ideas.domain.ai;

public final class RetryPolicy {
    private final int maxAttempts;
    public RetryPolicy(int maxAttempts) {
        if (maxAttempts <= 0) throw new IllegalArgumentException("maxAttempts must be positive");
        this.maxAttempts = maxAttempts;
    }
    public int maxAttempts() { return maxAttempts; }
    public boolean shouldRetry(String failureCode, int attempt) {
        if (attempt >= maxAttempts || failureCode == null) return false;
        return "NETWORK_ERROR".equals(failureCode) || "HTTP_429".equals(failureCode)
            || (failureCode.startsWith("HTTP_") && status(failureCode) >= 500);
    }
    private static int status(String failureCode) {
        try { return Integer.parseInt(failureCode.substring(5)); } catch (RuntimeException error) { return -1; }
    }
}
