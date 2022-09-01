package com.lastrix.scp.writeservice.service;

import com.lastrix.scp.writeservice.model.EnrolleeInfo;

import java.util.UUID;

public interface EnrolleeService {
    /**
     * Apply enrollee to spec
     *
     * @param userId    the enrollee
     * @param specId    the speciality id
     * @param sessionId session id or null for current
     * @return boolean
     */
    boolean enroll(UUID userId, UUID specId, Integer sessionId);

    /**
     * Confirm enrollee to spec (if enrolled)
     *
     * @param userId    the enrollee
     * @param specId    the speciality id
     * @param sessionId session id or null for current
     * @return boolean
     */
    boolean confirm(UUID userId, UUID specId, Integer sessionId);

    /**
     * Cancel enrollee application to spec (must be enrolled or confirmed)
     *
     * @param userId    the enrollee
     * @param specId    the speciality id
     * @param sessionId session id or null for current
     * @return boolean
     */
    boolean cancel(UUID userId, UUID specId, Integer sessionId);

    EnrolleeInfo getInfo(UUID userId, Integer sessionId);
}
