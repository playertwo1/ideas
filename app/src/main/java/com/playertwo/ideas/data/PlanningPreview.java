package com.playertwo.ideas.data;

import java.util.Collections;
import java.util.List;

public final class PlanningPreview {
    public final String batchId;
    public final List<String> itemIds;
    public final List<String> orphanMustIds;
    public final List<String> unlinkedFailureIds;
    public final List<String> duplicateWarnings;
    public final List<String> coverageErrors;
    public final boolean isReady;

    public PlanningPreview(String batchId, List<String> itemIds, List<String> orphanMustIds,
                           List<String> unlinkedFailureIds, List<String> duplicateWarnings,
                           List<String> coverageErrors, boolean isReady) {
        this.batchId = batchId;
        this.itemIds = Collections.unmodifiableList(itemIds);
        this.orphanMustIds = Collections.unmodifiableList(orphanMustIds);
        this.unlinkedFailureIds = Collections.unmodifiableList(unlinkedFailureIds);
        this.duplicateWarnings = Collections.unmodifiableList(duplicateWarnings);
        this.coverageErrors = Collections.unmodifiableList(coverageErrors);
        this.isReady = isReady;
    }
}
