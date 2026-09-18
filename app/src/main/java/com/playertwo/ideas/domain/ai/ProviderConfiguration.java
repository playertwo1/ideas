package com.playertwo.ideas.domain.ai;

import java.net.URI;

public final class ProviderConfiguration {
    private final URI endpoint;
    private final String providerId;
    private final String model;
    private final String secretReference;
    private final int timeoutMillis;

    public ProviderConfiguration(URI endpoint, String providerId, String model,
                                 String secretReference, int timeoutMillis) {
        if (endpoint == null || endpoint.getScheme() == null) throw new IllegalArgumentException("endpoint required");
        if (providerId == null || providerId.trim().isEmpty()) throw new IllegalArgumentException("providerId required");
        if (model == null || model.trim().isEmpty()) throw new IllegalArgumentException("model required");
        if (secretReference == null || secretReference.trim().isEmpty()) throw new IllegalArgumentException("secretReference required");
        if (timeoutMillis <= 0) throw new IllegalArgumentException("timeoutMillis must be positive");
        this.endpoint = endpoint; this.providerId = providerId; this.model = model;
        this.secretReference = secretReference; this.timeoutMillis = timeoutMillis;
    }
    public URI endpoint() { return endpoint; }
    public String providerId() { return providerId; }
    public String model() { return model; }
    public String secretReference() { return secretReference; }
    public int timeoutMillis() { return timeoutMillis; }
}
