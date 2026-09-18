package com.playertwo.ideas.domain.ai;

import java.util.HashMap;
import java.util.Map;

/** Only for tests; no production code may use this as a secure vault. */
public final class InMemorySecretStore implements SecretStore {
    private final Map<String, String> values = new HashMap<>();
    @Override public synchronized void put(String reference, String secret) {
        values.put(required(reference), required(secret));
    }
    @Override public synchronized String get(String reference) { return values.get(required(reference)); }
    @Override public synchronized void remove(String reference) { values.remove(required(reference)); }
    @Override public synchronized boolean contains(String reference) { return values.containsKey(required(reference)); }
    private static String required(String value) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException("reference/value required");
        return value;
    }
}
