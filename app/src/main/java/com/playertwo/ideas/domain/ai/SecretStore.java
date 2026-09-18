package com.playertwo.ideas.domain.ai;

/** Opaque secret reference; implementations own the storage and never expose secrets to logs. */
public interface SecretStore {
    void put(String reference, String secret);
    String get(String reference);
    void remove(String reference);
    boolean contains(String reference);
}
