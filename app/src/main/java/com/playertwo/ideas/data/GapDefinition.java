package com.playertwo.ideas.data;

import androidx.annotation.NonNull;

/** Provider-neutral catalog entry for one interview gap. */
public final class GapDefinition {
    @NonNull public final String gapId;
    @NonNull public final String criticality;
    @NonNull public final String prompt;
    @NonNull public final String options;
    @NonNull public final String recommendation;
    @NonNull public final String tradeoffs;
    public final String defaultValue;

    public GapDefinition(String gapId, String criticality, String prompt, String options,
                         String recommendation, String tradeoffs, String defaultValue) {
        this.gapId = gapId; this.criticality = criticality; this.prompt = prompt;
        this.options = options; this.recommendation = recommendation; this.tradeoffs = tradeoffs;
        this.defaultValue = defaultValue;
    }
}
