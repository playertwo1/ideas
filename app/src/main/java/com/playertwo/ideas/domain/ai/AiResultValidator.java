package com.playertwo.ideas.domain.ai;

public final class AiResultValidator {
    private AiResultValidator() { }
    public static void validate(AiRequest request, AiResult result) {
        if (result == null || result.outcome() != AiResult.Outcome.SUCCESS) throw new IllegalArgumentException("result is not successful");
        if (!request.projectId().equals(result.references().get("projectId"))) throw new IllegalArgumentException("projectId mismatch");
        if (!Long.toString(request.inputRevision()).equals(result.references().get("inputRevision"))) throw new IllegalArgumentException("inputRevision mismatch");
        if (!request.schemaVersion().equals(result.references().get("schemaVersion"))) throw new IllegalArgumentException("schemaVersion mismatch");
        if (result.content().containsKey("gate") || result.content().containsKey("approval") || result.content().containsKey("locked"))
            throw new IllegalArgumentException("provider cannot assign authority");
    }
}
