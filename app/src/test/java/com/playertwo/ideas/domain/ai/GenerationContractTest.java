package com.playertwo.ideas.domain.ai;

import org.junit.Test;
import java.net.URI;
import java.util.Collections;
import static org.junit.Assert.*;

public class GenerationContractTest {
    private AiRequest request() {
        return new AiRequest("INTERPRET", "P1", 3, "0.1",
            Collections.singletonMap("idea", "texto"), Collections.singletonMap("mode", "STANDARD"), 128, "req-1");
    }
    private PromptRegistry prompts() {
        PromptRegistry registry = new PromptRegistry();
        registry.register(new PromptTemplate("interpret", "v1", "Interpret only: {{idea}}"));
        return registry;
    }

    @Test public void httpAdapterUsesExternalSecretAndMapsTransportFailure() {
        InMemorySecretStore secrets = new InMemorySecretStore(); secrets.put("key", "secret-value");
        final String[] authorization = new String[1];
        HttpTransport transport = (endpoint, headers, body, timeout) -> {
            authorization[0] = headers.get("Authorization");
            assertTrue(body.contains("\"projectId\":\"P1\""));
            return new HttpResponse(401, "secret-value");
        };
        AiProvider provider = new HttpAiProviderAdapter(new ProviderConfiguration(URI.create("https://provider.invalid"), "p", "m", "key", 100), secrets, transport);
        AiResult result = provider.generate(request(), CancellationToken.never());
        assertEquals(AiResult.Outcome.FAILED, result.outcome());
        assertEquals("Bearer secret-value", authorization[0]);
        assertEquals("HTTP_401", result.failureCode());
    }

    @Test public void httpAdapterMapsAuthFailureWithoutExposingSecret() {
        InMemorySecretStore secrets = new InMemorySecretStore(); secrets.put("key", "secret-value");
        HttpTransport transport = (endpoint, headers, body, timeout) -> new HttpResponse(401, "secret-value");
        AiResult result = new HttpAiProviderAdapter(new ProviderConfiguration(URI.create("https://provider.invalid"), "p", "m", "key", 100), secrets, transport)
            .generate(request(), CancellationToken.never());
        assertEquals(AiResult.Outcome.FAILED, result.outcome());
        assertEquals("HTTP_401", result.failureCode());
        assertFalse(String.valueOf(result.failureCode()).contains("secret-value"));
    }

    @Test public void httpAdapterMapsForbiddenRateAndServerFailures() {
        InMemorySecretStore secrets = new InMemorySecretStore(); secrets.put("key", "secret-value");
        for (int status : new int[]{403, 429, 500, 503}) {
            final int responseStatus = status;
            HttpTransport transport = (endpoint, headers, body, timeout) -> new HttpResponse(responseStatus, "");
            AiResult result = new HttpAiProviderAdapter(new ProviderConfiguration(URI.create("https://provider.invalid"), "p", "m", "key", 100), secrets, transport)
                .generate(request(), CancellationToken.never());
            assertEquals(AiResult.Outcome.FAILED, result.outcome());
            assertEquals("HTTP_" + status, result.failureCode());
        }
    }

    @Test public void coordinatorRequiresConsentAndPersistsSuccessfulRun() {
        InMemoryConsentManager consent = new InMemoryConsentManager();
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        try {
            new GenerationCoordinator().execute("run-1", request(), "interpret", new FakeAiProvider(), "fake", consent, prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
            fail("missing consent accepted");
        } catch (IllegalStateException expected) { }
        assertNull(store.find("run-1"));
        consent.grant("P1");
        GenerationRun run = new GenerationCoordinator().execute("run-1", request(), "interpret", new FakeAiProvider(), "fake", consent, prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
        assertEquals(GenerationStatus.SUCCEEDED, run.status());
        assertEquals("v1", run.promptVersion());
        assertEquals(128, run.requestedTokens());
        assertSame(run, store.find("run-1"));
    }

    @Test public void coordinatorReplayReturnsPersistedResultWithoutProviderReplay() {
        InMemoryConsentManager consent = new InMemoryConsentManager(); consent.grant("P1");
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        final int[] calls = new int[1];
        AiProvider provider = (request, token) -> { calls[0]++; return new FakeAiProvider().generate(request, token); };
        GenerationCoordinator coordinator = new GenerationCoordinator();
        GenerationRun first = coordinator.execute("run-2", request(), "interpret", provider, "fake", consent, prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
        GenerationRun replay = coordinator.execute("run-2", request(), "interpret", provider, "fake", consent, prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
        assertEquals(1, calls[0]); assertEquals(first.status(), replay.status()); assertEquals(first.result(), replay.result());
    }

    @Test public void coordinatorTimeoutPersistsWithoutPartialResult() {
        InMemoryConsentManager consent = new InMemoryConsentManager(); consent.grant("P1");
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        AiProvider slow = (request, token) -> { try { Thread.sleep(200); } catch (InterruptedException expected) { Thread.currentThread().interrupt(); } return new FakeAiProvider().generate(request, token); };
        GenerationRun run = new GenerationCoordinator().execute("run-3", request(), "interpret", slow, "slow", consent, prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 10);
        assertEquals(GenerationStatus.TIMED_OUT, run.status()); assertNull(run.result()); assertEquals("TIMEOUT", run.failureCode());
    }

    @Test public void coordinatorCancellationPersistsExplicitState() {
        InMemoryConsentManager consent = new InMemoryConsentManager(); consent.grant("P1");
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        GenerationRun run = new GenerationCoordinator().execute("run-cancel", request(), "interpret", new FakeAiProvider(), "fake", consent, prompts(), new GenerationBudget(128, 100, 0), store, () -> true, 1000);
        assertEquals(GenerationStatus.CANCELLED, run.status()); assertEquals("CANCELLED", run.failureCode()); assertTrue(run.result().content().isEmpty());
    }

    @Test public void coordinatorRejectsBudgetAndMismatchedResultBeforeSuccess() {
        InMemoryConsentManager consent = new InMemoryConsentManager(); consent.grant("P1");
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        try {
            new GenerationCoordinator().execute("run-4", request(), "interpret", new FakeAiProvider(), "fake", consent, prompts(), new GenerationBudget(1, 100, 0), store, CancellationToken.never(), 1000);
            fail("budget exceeded accepted");
        } catch (IllegalArgumentException expected) { }
        assertNull(store.find("run-4"));
        AiProvider wrongProject = (req, token) -> AiResult.success(Collections.singletonMap("suggestion", "x"),
            new java.util.HashMap<String, String>() {{ put("projectId", "P2"); put("inputRevision", "3"); put("schemaVersion", "0.1"); }},
            Collections.emptyList(), "x");
        GenerationRun run = new GenerationCoordinator().execute("run-5", request(), "interpret", wrongProject, "fake", consent, prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
        assertEquals(GenerationStatus.REJECTED, run.status()); assertEquals("INVALID_RESULT", run.failureCode());
    }

    @Test public void coordinatorRetriesTransientFailureAndPersistsAttempts() {
        InMemoryConsentManager consent = new InMemoryConsentManager(); consent.grant("P1");
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        final int[] calls = new int[1];
        AiProvider flaky = (req, token) -> {
            calls[0]++;
            if (calls[0] < 3) return AiResult.failed("attempt-" + calls[0], "HTTP_429");
            return new FakeAiProvider().generate(req, token);
        };
        GenerationRun run = new GenerationCoordinator().execute("run-retry", request(), "interpret", flaky, "fake", consent,
            prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
        assertEquals(GenerationStatus.SUCCEEDED, run.status());
        assertEquals(3, calls[0]);
        assertEquals(3, run.attempts());
        assertEquals(3, store.find("run-retry").attempts());
    }

    @Test public void coordinatorBoundsTransientRetriesAndDoesNotRetryAuthFailure() {
        InMemoryConsentManager consent = new InMemoryConsentManager(); consent.grant("P1");
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        final int[] calls = new int[1];
        AiProvider unavailable = (req, token) -> { calls[0]++; return AiResult.failed("attempt", "HTTP_503"); };
        GenerationRun run = new GenerationCoordinator().execute("run-limit", request(), "interpret", unavailable, "fake", consent,
            prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
        assertEquals(GenerationStatus.FAILED, run.status());
        assertEquals(3, calls[0]);
        assertEquals(3, run.attempts());

        final int[] authCalls = new int[1];
        AiProvider unauthorized = (req, token) -> { authCalls[0]++; return AiResult.failed("auth", "HTTP_401"); };
        GenerationRun authRun = new GenerationCoordinator().execute("run-auth", request(), "interpret", unauthorized, "fake", consent,
            prompts(), new GenerationBudget(128, 100, 0), store, CancellationToken.never(), 1000);
        assertEquals(GenerationStatus.FAILED, authRun.status());
        assertEquals(1, authCalls[0]);
        assertEquals(1, authRun.attempts());
    }

    @Test public void coordinatorRejectsProviderCostBeforeAcceptingResult() {
        InMemoryConsentManager consent = new InMemoryConsentManager(); consent.grant("P1");
        InMemoryGenerationRunStore store = new InMemoryGenerationRunStore();
        AiProvider costly = (req, token) -> AiResult.success(Collections.singletonMap("suggestion", "x"),
            java.util.Collections.emptyMap(), java.util.Collections.emptyList(), "costly", 101);
        GenerationRun run = new GenerationCoordinator().execute("run-cost", request(), "interpret", costly, "fake", consent,
            prompts(), new GenerationBudget(128, 100, 100), store, CancellationToken.never(), 1000);
        assertEquals(GenerationStatus.REJECTED, run.status());
        assertEquals("COST_BUDGET_EXCEEDED", run.failureCode());
        assertNull(run.result());
        assertEquals(101, run.usageCostMicros());
    }
}
