package com.playertwo.ideas.domain.ai;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryGenerationRunStore implements GenerationRunStore {
    private final Map<String, GenerationRun> runs = new HashMap<>();
    @Override public synchronized GenerationRun find(String runId) { return runs.get(runId); }
    @Override public synchronized void save(GenerationRun run) {
        if (run == null || run.runId() == null || run.runId().trim().isEmpty()) throw new IllegalArgumentException("run required");
        runs.put(run.runId(), run);
    }
}
