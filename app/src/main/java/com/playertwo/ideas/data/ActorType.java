package com.playertwo.ideas.data;

import java.util.Locale;

/** Structured actor classification used for authority-sensitive transitions. */
public enum ActorType {
    HUMAN,
    AI,
    SYSTEM,
    BOT,
    AUTOMATION,
    AI_AGENT;

    public static ActorType parse(String raw) {
        if (raw == null || raw.trim().isEmpty()) throw new IllegalArgumentException("actor required");
        String value = raw.trim().toUpperCase(Locale.ROOT);
        if ("USER".equals(value)) return HUMAN;
        try {
            return valueOf(value);
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("unknown actor type", error);
        }
    }
}
