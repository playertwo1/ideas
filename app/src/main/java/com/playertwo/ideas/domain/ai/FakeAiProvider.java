package com.playertwo.ideas.domain.ai;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/** Offline provider for tests; no network, credential, Android or vendor dependency. */
public final class FakeAiProvider implements AiProvider {
    @Override public AiResult generate(AiRequest request, CancellationToken cancellationToken) {
        if (request == null || cancellationToken == null) throw new IllegalArgumentException("request and cancellationToken required");
        String executionId = "fake-" + sha256(request.canonical());
        if (cancellationToken.isCancelled()) return AiResult.cancelled(executionId);
        Map<String, String> content = new LinkedHashMap<>();
        content.put("operation", request.operation());
        content.put("suggestion", "deterministic-" + executionId.substring(5, 21));
        Map<String, String> references = new LinkedHashMap<>();
        references.put("projectId", request.projectId());
        references.put("inputRevision", Long.toString(request.inputRevision()));
        references.put("schemaVersion", request.schemaVersion());
        return AiResult.success(content, references,
            Arrays.asList("fake-provider", "unverified-suggestion"), executionId);
    }

    private static String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                .digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte item : digest) result.append(String.format("%02x", item & 0xff));
            return result.toString();
        } catch (NoSuchAlgorithmException error) { throw new AssertionError(error); }
    }
}
