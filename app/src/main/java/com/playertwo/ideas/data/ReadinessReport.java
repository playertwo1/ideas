package com.playertwo.ideas.data;

import java.util.Collections;
import java.util.List;

public final class ReadinessReport {
    public final String projectId;
    public final boolean ready;
    public final String overallStatus;
    public final List<String> errors;
    public final List<String> pendingDimensions;
    public final List<String> actionablePending;

    public ReadinessReport(String projectId, boolean ready, String overallStatus, List<String> errors,
                           List<String> pendingDimensions, List<String> actionablePending) {
        this.projectId = projectId; this.ready = ready; this.overallStatus = overallStatus;
        this.errors = Collections.unmodifiableList(errors);
        this.pendingDimensions = Collections.unmodifiableList(pendingDimensions);
        this.actionablePending = Collections.unmodifiableList(actionablePending);
    }
}
