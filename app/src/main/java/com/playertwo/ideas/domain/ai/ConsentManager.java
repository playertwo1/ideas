package com.playertwo.ideas.domain.ai;

public interface ConsentManager {
    boolean isGranted(String projectId);
    void grant(String projectId);
    void revoke(String projectId);
}
