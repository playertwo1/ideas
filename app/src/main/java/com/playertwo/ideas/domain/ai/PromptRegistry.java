package com.playertwo.ideas.domain.ai;

import java.util.HashMap;
import java.util.Map;

public final class PromptRegistry {
    private final Map<String, PromptTemplate> templates = new HashMap<>();
    public synchronized void register(PromptTemplate template) {
        if (template == null) throw new IllegalArgumentException("template required");
        if (templates.containsKey(template.id())) throw new IllegalStateException("prompt already registered");
        templates.put(template.id(), template);
    }
    public synchronized PromptTemplate get(String id) {
        if (id == null || !templates.containsKey(id)) throw new IllegalArgumentException("prompt not registered");
        return templates.get(id);
    }
}
