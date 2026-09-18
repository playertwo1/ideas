package com.playertwo.ideas.domain.ai;

public interface GenerationRunStore {
    GenerationRun find(String runId);
    void save(GenerationRun run);
}
