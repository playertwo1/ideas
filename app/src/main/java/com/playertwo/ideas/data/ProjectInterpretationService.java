package com.playertwo.ideas.data;

import com.playertwo.ideas.domain.ai.AiProvider;
import com.playertwo.ideas.domain.ai.AiRequest;
import com.playertwo.ideas.domain.ai.AiResult;
import com.playertwo.ideas.domain.ai.AiResultValidator;
import com.playertwo.ideas.domain.ai.CancellationToken;
import java.util.LinkedHashMap;
import java.util.Map;

/** Generates an editable suggestion; acceptance remains an explicit user action. */
public final class ProjectInterpretationService {
    private final ProjectIntakeRepository intake;

    public ProjectInterpretationService(ProjectIntakeRepository intake) {
        if (intake == null) throw new IllegalArgumentException("intake required");
        this.intake = intake;
    }

    public void suggest(String projectId, AiProvider provider, CancellationToken cancellationToken) {
        if (provider == null || cancellationToken == null) throw new IllegalArgumentException("provider and cancellation required");
        ProjectIntakeEntity source = intake.find(projectId);
        if (source == null) throw new IllegalArgumentException("unknown projectId");
        Map<String, String> context = new LinkedHashMap<>();
        context.put("title", source.originalTitle);
        context.put("idea", source.originalIdea);
        Map<String, String> decisions = new LinkedHashMap<>();
        decisions.put("limits", source.originalLimits);
        AiRequest request = new AiRequest("INTERPRET", source.projectId, 0, "0.1", context, decisions, 128,
            "intake-" + source.originalHash.substring(0, 16));
        AiResult result = provider.generate(request, cancellationToken);
        if (result == null || result.outcome() != AiResult.Outcome.SUCCESS)
            throw new IllegalStateException(result == null ? "provider returned no result" : result.failureCode());
        AiResultValidator.validate(request, result);
        String suggestion = result.content().get("suggestion");
        if (suggestion == null || suggestion.trim().isEmpty()) throw new IllegalStateException("suggestion missing");
        intake.updateSuggestion(source.projectId, suggestion, "GENERAL", source.originalLimits,
            "STANDARD", "Sugestão gerada; revise antes de aceitar");
    }
}
