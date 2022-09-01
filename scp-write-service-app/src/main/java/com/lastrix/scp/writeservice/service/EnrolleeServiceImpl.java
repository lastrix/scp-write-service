package com.lastrix.scp.writeservice.service;

import com.lastrix.scp.writeservice.dao.EnrolleeDao;
import com.lastrix.scp.writeservice.dao.SessionDao;
import com.lastrix.scp.writeservice.model.EnrolleeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EnrolleeServiceImpl implements EnrolleeService {
    @Autowired
    private EnrolleeDao enrolleeDao;
    @Autowired
    private SessionDao sessionDao;

    @Transactional
    @Override
    public boolean enroll(UUID userId, UUID specId, Integer sessionId) {
        if (sessionId == null) {
            sessionId = sessionDao.getLatestSessionId();
        }
        return enrolleeDao.enroll(userId, specId, sessionId);
    }

    @Transactional
    @Override
    public boolean confirm(UUID userId, UUID specId, Integer sessionId) {
        if (sessionId == null) {
            sessionId = sessionDao.getLatestSessionId();
        }
        return enrolleeDao.confirm(userId, specId, sessionId);
    }

    @Transactional
    @Override
    public boolean cancel(UUID userId, UUID specId, Integer sessionId) {
        if (sessionId == null) {
            sessionId = sessionDao.getLatestSessionId();
        }
        return enrolleeDao.cancel(userId, specId, sessionId);
    }

    @Transactional(readOnly = true)
    @Override
    public EnrolleeInfo getInfo(UUID userId, Integer sessionId) {
        if (sessionId == null) {
            sessionId = sessionDao.getLatestSessionId();
        }
        return enrolleeDao.getInfo(userId, sessionId);
    }
}
