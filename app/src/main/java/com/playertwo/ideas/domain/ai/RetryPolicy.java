package com.playertwo.ideas.domain.ai;

/** Bounded retry policy for transient provider failures only. */
public final class RetryPolicy {
    private final int maxRetries;
    private final long backoffMillis;

    public RetryPolicy(int maxRetries) { this(maxRetries, 0); }

    public RetryPolicy(int maxRetries, long backoffMillis) {
        if (maxRetries < 0 || backoffMillis < 0) throw new IllegalArgumentException("invalid retry policy");
        this.maxRetries = maxRetries;
        this.backoffMillis = backoffMillis;
    }

    public static RetryPolicy transientDefaults() { return new RetryPolicy(2, 0); }
    public int maxRetries() { return maxRetries; }
    public long backoffMillis() { return backoffMillis; }

    public boolean shouldRetry(String failureCode, int attempts) {
        if (attempts > maxRetries) return false;
        return "NETWORK_ERROR".equals(failureCode)
            || "EXECUTION_ERROR".equals(failureCode)
            || "HTTP_429".equals(failureCode)
            || (failureCode != null && failureCode.matches("HTTP_5\\d{2}"));
    }
}
