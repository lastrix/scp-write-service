package com.lastrix.scp.writeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolleSelectInfo {
    private UUID specId;
    private short status;
    private int score;
    private Instant createdStamp;
    private Instant confirmedStamp;
    private Instant cancelledStamp;
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
