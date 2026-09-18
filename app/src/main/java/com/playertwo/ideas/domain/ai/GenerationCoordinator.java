package com.playertwo.ideas.domain.ai;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/** Coordinates one idempotent generation attempt; applying a result is a later domain decision. */
public final class GenerationCoordinator {
    public GenerationRun execute(String runId, AiRequest request, String promptId, AiProvider provider,
                                  String providerId, ConsentManager consent, PromptRegistry prompts,
                                  GenerationBudget budget, GenerationRunStore store,
                                  CancellationToken cancellationToken, long timeoutMillis) {
        required(runId, "runId"); required(providerId, "providerId");
        if (provider == null || consent == null || prompts == null || budget == null || store == null || cancellationToken == null)
            throw new IllegalArgumentException("coordinator dependencies required");
        GenerationRun previous = store.find(runId);
        if (previous != null) {
            if (previous.terminal()) return previous;
            throw new IllegalStateException("generation already running");
        }
        if (timeoutMillis <= 0) throw new IllegalArgumentException("timeoutMillis must be positive");
        if (!consent.isGranted(request.projectId())) throw new IllegalStateException("consent required");
        PromptTemplate prompt = prompts.get(promptId);
        budget.validate(request);
        store.save(new GenerationRun(runId, request.projectId(), request.inputRevision(), prompt.id(), prompt.version(),
            providerId, GenerationStatus.RUNNING, null, null, request.maxTokens(), 0));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<AiResult> future = executor.submit(() -> provider.generate(request, cancellationToken));
        try {
            AiResult result = future.get(timeoutMillis, TimeUnit.MILLISECONDS);
            if (result.outcome() == AiResult.Outcome.CANCELLED) return finish(store, runId, request, prompt, providerId, GenerationStatus.CANCELLED, result, result.failureCode());
            if (result.outcome() == AiResult.Outcome.FAILED) return finish(store, runId, request, prompt, providerId, GenerationStatus.FAILED, result, result.failureCode());
            AiResultValidator.validate(request, result);
            return finish(store, runId, request, prompt, providerId, GenerationStatus.SUCCEEDED, result, null);
        } catch (TimeoutException error) {
            future.cancel(true); return finish(store, runId, request, prompt, providerId, GenerationStatus.TIMED_OUT, null, "TIMEOUT");
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt(); future.cancel(true);
            return finish(store, runId, request, prompt, providerId, GenerationStatus.CANCELLED, null, "INTERRUPTED");
        } catch (ExecutionException error) {
            return finish(store, runId, request, prompt, providerId, GenerationStatus.FAILED, null, "EXECUTION_ERROR");
        } catch (RuntimeException error) {
            return finish(store, runId, request, prompt, providerId, GenerationStatus.REJECTED, null, "INVALID_RESULT");
        } finally { executor.shutdownNow(); }
    }

    private static GenerationRun finish(GenerationRunStore store, String runId, AiRequest request,
                                        PromptTemplate prompt, String providerId, GenerationStatus status,
                                        AiResult result, String failure) {
        GenerationRun run = new GenerationRun(runId, request.projectId(), request.inputRevision(), prompt.id(), prompt.version(), providerId, status, result, failure, request.maxTokens(), 0);
        store.save(run); return run;
    }
    private static String required(String value, String name) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(name + " required");
        return value;
    }
}
