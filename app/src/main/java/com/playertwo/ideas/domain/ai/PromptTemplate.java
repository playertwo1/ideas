package com.playertwo.ideas.domain.ai;

public final class PromptTemplate {
    private final String id;
    private final String version;
    private final String template;
    public PromptTemplate(String id, String version, String template) {
        this.id = required(id, "id"); this.version = required(version, "version"); this.template = required(template, "template");
    }
    public String id() { return id; }
    public String version() { return version; }
    public String template() { return template; }
    private static String required(String value, String name) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(name + " required");
        return value;
    }
}
