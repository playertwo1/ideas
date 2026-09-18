package com.playertwo.ideas.domain.ai;

/** Provider-neutral boundary for a structured suggestion operation. */
public interface AiProvider {
    AiResult generate(AiRequest request, CancellationToken cancellationToken);
}
