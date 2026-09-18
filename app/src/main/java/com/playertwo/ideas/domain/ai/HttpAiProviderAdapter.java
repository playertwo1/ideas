package com.playertwo.ideas.domain.ai;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Optional real-provider adapter. The Core only sees AiProvider. */
public final class HttpAiProviderAdapter implements AiProvider {
    private final ProviderConfiguration configuration;
    private final SecretStore secrets;
    private final HttpTransport transport;

    public HttpAiProviderAdapter(ProviderConfiguration configuration, SecretStore secrets) {
        this(configuration, secrets, new UrlConnectionTransport());
    }
    public HttpAiProviderAdapter(ProviderConfiguration configuration, SecretStore secrets, HttpTransport transport) {
        if (configuration == null || secrets == null || transport == null) throw new IllegalArgumentException("adapter dependencies required");
        this.configuration = configuration; this.secrets = secrets; this.transport = transport;
    }

    @Override public AiResult generate(AiRequest request, CancellationToken cancellationToken) {
        if (request == null || cancellationToken == null) throw new IllegalArgumentException("request and cancellationToken required");
        String executionId = "http-" + request.requestId();
        if (cancellationToken.isCancelled()) return AiResult.cancelled(executionId);
        String secret = secrets.get(configuration.secretReference());
        if (secret == null) return AiResult.failed(executionId, "CREDENTIAL_MISSING");
        try {
            Map<String, String> headers = new HashMap<>(); headers.put("Authorization", "Bearer " + secret);
            HttpResponse response = transport.post(configuration.endpoint(), headers, requestBody(request), configuration.timeoutMillis());
            if (response.statusCode() == 401 || response.statusCode() == 403 || response.statusCode() == 429 || response.statusCode() >= 500)
                return AiResult.failed(executionId, "HTTP_" + response.statusCode());
            if (response.statusCode() < 200 || response.statusCode() >= 300) return AiResult.failed(executionId, "HTTP_" + response.statusCode());
            if (cancellationToken.isCancelled()) return AiResult.cancelled(executionId);
            return parseSuccess(executionId, response.body());
        } catch (IOException error) { return AiResult.failed(executionId, "NETWORK_ERROR"); }
          catch (Exception error) { return AiResult.failed(executionId, "INVALID_RESPONSE"); }
    }

    private String requestBody(AiRequest request) throws Exception {
        return "{\"model\":" + quote(configuration.model()) + ",\"operation\":" + quote(request.operation())
            + ",\"projectId\":" + quote(request.projectId()) + ",\"inputRevision\":" + request.inputRevision()
            + ",\"schemaVersion\":" + quote(request.schemaVersion()) + ",\"requestId\":" + quote(request.requestId())
            + ",\"context\":" + object(request.context()) + ",\"decisions\":" + object(request.decisions())
            + ",\"maxTokens\":" + request.maxTokens() + "}";
    }

    private static String object(Map<String, String> values) {
        StringBuilder result = new StringBuilder("{"); boolean first = true;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (!first) result.append(','); first = false;
            result.append(quote(entry.getKey())).append(':').append(quote(entry.getValue()));
        }
        return result.append('}').toString();
    }

    private static String quote(String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\"";
    }

    private AiResult parseSuccess(String executionId, String raw) throws Exception {
        JSONObject body = new JSONObject(raw);
        JSONObject rawContent = body.getJSONObject("content");
        JSONObject rawReferences = body.getJSONObject("references");
        Map<String, String> content = strings(rawContent); Map<String, String> references = strings(rawReferences);
        JSONArray rawHypotheses = body.optJSONArray("hypotheses"); List<String> hypotheses = new ArrayList<>();
        if (rawHypotheses != null) for (int i = 0; i < rawHypotheses.length(); i++) hypotheses.add(rawHypotheses.getString(i));
        return AiResult.success(content, references, hypotheses, executionId);
    }

    private static Map<String, String> strings(JSONObject object) throws Exception {
        Map<String, String> values = new LinkedHashMap<>();
        java.util.Iterator<String> keys = object.keys();
        while (keys.hasNext()) { String key = keys.next(); values.put(key, object.getString(key)); }
        return values;
    }
}
