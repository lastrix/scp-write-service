package com.lastrix.scp.writeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Enrollee's selected speciality")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolleSelectInfo {
    @Schema(description = "Speciality id")
    private UUID specId;
    @Schema(description = "Current selection status, 0 for applied, 1 for confirmed and 2 for cancelled")
    private short status;
    @Schema(description = "Assigned enrollee's score, the greater the better")
    private int score;
    @Schema(description = "Date of enrollee's application to this speciality")
    private Instant createdStamp;
    @Schema(description = "Date of enrollee's confirmation")
    private Instant confirmedStamp;
    @Schema(description = "Date of enrollee's cancelling")
    private Instant cancelledStamp;
    @Schema(description = "State of sync, 0 if not sent, 1 for sent but not confirmed, 2 for sent and confirmed")
    private int state;

    public UUID getSpecId() {
        return specId;
    }

    public void setSpecId(UUID specId) {
        this.specId = specId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Instant getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Instant createdStamp) {
        this.createdStamp = createdStamp;
    }

    public Instant getConfirmedStamp() {
        return confirmedStamp;
    }

    public void setConfirmedStamp(Instant confirmedStamp) {
        this.confirmedStamp = confirmedStamp;
    }

    public Instant getCancelledStamp() {
        return cancelledStamp;
    }

    public void setCancelledStamp(Instant cancelledStamp) {
        this.cancelledStamp = cancelledStamp;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EnrolleSelectInfo{" +
                "specId=" + specId +
                ", status=" + status +
                ", score=" + score +
                ", createdStamp=" + createdStamp +
                ", confirmedStamp=" + confirmedStamp +
                ", cancelledStamp=" + cancelledStamp +
                ", state=" + state +
                '}';
    }
}
