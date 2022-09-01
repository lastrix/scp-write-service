package com.lastrix.scp.writeservice.dao;

import com.lastrix.scp.writeservice.model.EnrolleeInfo;

import java.util.UUID;

public interface EnrolleeDao {
    boolean enroll(UUID userId, UUID specId, int sessionId);

    boolean confirm(UUID userId, UUID specId, int sessionId);

    boolean cancel(UUID userId, UUID specId, int sessionId);

    EnrolleeInfo getInfo(UUID userId, int sessionId);
}
