package com.lastrix.scp.writeservice.dao;

import com.lastrix.scp.lib.rest.error.ServiceErrorException;
import com.lastrix.scp.writeservice.model.EnrolleSelectInfo;
import com.lastrix.scp.writeservice.model.EnrolleeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Repository
public class PostgreEnrolleeDao implements EnrolleeDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgreEnrolleeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean enroll(UUID userId, UUID specId, int sessionId) {
        try {
            return 1 == jdbcTemplate.update(
                    "INSERT INTO scp_write_service.enrollee_select(user_id, session_id, spec_id) VALUES (?, ?, ?)",
                    userId, sessionId, specId);
        } catch (Exception e) {
            log.error("Failed to enroll", e);
            return false;
        }
    }

    @Override
    public boolean confirm(UUID userId, UUID specId, int sessionId) {
        return 1 == jdbcTemplate.update(
                "UPDATE scp_write_service.enrollee_select SET status = 1 WHERE user_id = ? AND spec_id = ? AND session_id = ?",
                userId, specId, sessionId);
    }

    @Override
    public boolean cancel(UUID userId, UUID specId, int sessionId) {
        return 1 == jdbcTemplate.update(
                "UPDATE scp_write_service.enrollee_select SET status = 2 WHERE user_id = ? AND spec_id = ? AND session_id = ?",
                userId, specId, sessionId);
    }

    @Override
    public EnrolleeInfo getInfo(UUID userId, int sessionId) {
        var info = jdbcTemplate.queryForObject("SELECT user_id, session_id, disabled, selected_spec_id, selection_count, created_stamp, modified_stamp FROM scp_write_service.enrollee es WHERE es.user_id = ? AND es.session_id = ?",
                new Object[]{userId, sessionId},
                (rs, rowNum) -> {
                    var r = new EnrolleeInfo();
                    r.setUserId(UUID.fromString(rs.getString(1)));
                    r.setSessionId(rs.getInt(2));
                    r.setDisabled(rs.getBoolean(3));
                    r.setSelectedSpecId(UUID.fromString(rs.getString(4)));
                    r.setSelectionCount(rs.getInt(5));
                    r.setCreatedStamp(toInstantOrNull(rs, 6));
                    r.setModifiedStamp(toInstantOrNull(rs, 7));
                    return r;
                });
        if (info == null) {
            throw ServiceErrorException.notFound("Enrollee", userId + "-" + sessionId);
        }
        var list = jdbcTemplate.query("SELECT spec_id, status, score, created_stamp, confirmed_stamp, canceled_stamp, state FROM scp_write_service.enrollee_select es WHERE es.user_id = ? AND es.session_id = ? ORDER BY es.spec_id",
                new Object[]{userId, sessionId},
                (rs, rowNum) -> {
                    var r = new EnrolleSelectInfo();
                    r.setSpecId(UUID.fromString(rs.getString(1)));
                    r.setStatus(rs.getInt(2));
                    r.setScore(rs.getInt(3));
                    r.setCreatedStamp(toInstantOrNull(rs, 4));
                    r.setConfirmedStamp(toInstantOrNull(rs, 5));
                    r.setCancelledStamp(toInstantOrNull(rs, 6));
                    r.setState(rs.getInt(7));
                    return r;
                });
        info.setSelects(list);
        return info;
    }

    private Instant toInstantOrNull(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnIndex);
        return ts == null ? null : ts.toInstant();
    }
}
