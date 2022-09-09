package com.lastrix.scp.writeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Schema(description = "Describes enrollee and his selected specialities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolleeInfo {
    @Schema(description = "Enrollee's user id")
    private UUID userId;
    @Schema(description = "Selection campaign id")
    private int sessionId;
    @Schema(description = "Starts from 20, with each application reduced by 1, must be ge than zero")
    private int selectionCount;
    @Schema(description = "Only single speciality may be confirmed, here its id stored")
    private UUID selectedSpecId;
    @Schema(description = "True if this enrollee may no longer do anything")
    private boolean disabled;
    @Schema(description = "Date of enrollee registration whithin system")
    private Instant createdStamp;
    @Schema(description = "Last modification stamp")
    private Instant modifiedStamp;
    @Schema(description = "List of enrollee's selected specialities")
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
