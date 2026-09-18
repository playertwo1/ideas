package com.playertwo.ideas.domain.ai;

@FunctionalInterface
public interface CancellationToken {
    boolean isCancelled();

    static CancellationToken never() { return () -> false; }
}
