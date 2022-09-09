package com.lastrix.scp.writeservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgreSessionDao implements SessionDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgreSessionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getLatestSessionId() {
        List<Integer> list = jdbcTemplate.query(
                "SELECT session_id FROM scp_write_service.session s WHERE s.active = true ORDER BY s.session_id DESC LIMIT 1",
                (rs, rowNum) -> rs.getInt(1));
        return list.isEmpty() ? -1 : list.get(0);
    }
}
