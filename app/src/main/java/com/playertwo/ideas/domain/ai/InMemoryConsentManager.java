package com.playertwo.ideas.domain.ai;

import java.util.HashSet;
import java.util.Set;

/** Test/local implementation; production storage is supplied by an adapter. */
public final class InMemoryConsentManager implements ConsentManager {
    private final Set<String> granted = new HashSet<>();
    @Override public boolean isGranted(String projectId) { return granted.contains(required(projectId)); }
    @Override public void grant(String projectId) { granted.add(required(projectId)); }
    @Override public void revoke(String projectId) { granted.remove(required(projectId)); }
    private static String required(String value) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException("projectId required");
        return value;
    }
}
