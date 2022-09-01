package com.lastrix.scp.writeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolleeInfo {
    private UUID userId;
    private int sessionId;
    private int selectionCount;
    private UUID selectedSpecId;
    private boolean disabled;
    private Instant createdStamp;
    private Instant modifiedStamp;
    private List<EnrolleSelectInfo> selects;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSelectionCount() {
        return selectionCount;
    }

    public void setSelectionCount(int selectionCount) {
        this.selectionCount = selectionCount;
    }

    public UUID getSelectedSpecId() {
        return selectedSpecId;
    }

    public void setSelectedSpecId(UUID selectedSpecId) {
        this.selectedSpecId = selectedSpecId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Instant getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Instant createdStamp) {
        this.createdStamp = createdStamp;
    }

    public Instant getModifiedStamp() {
        return modifiedStamp;
    }

    public void setModifiedStamp(Instant modifiedStamp) {
        this.modifiedStamp = modifiedStamp;
    }

    public List<EnrolleSelectInfo> getSelects() {
        return selects;
    }

    public void setSelects(List<EnrolleSelectInfo> selects) {
        this.selects = selects;
    }

    @Override
    public String toString() {
        return "EnrolleeInfo{" +
                "userId=" + userId +
                ", sessionId=" + sessionId +
                ", selectionCount=" + selectionCount +
                ", selectedSpecId=" + selectedSpecId +
                ", disabled=" + disabled +
                ", createdStamp=" + createdStamp +
                ", modifiedStamp=" + modifiedStamp +
                '}';
    }
}
