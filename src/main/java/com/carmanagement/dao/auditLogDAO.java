package com.carmanagement.dao;

import com.carmanagement.entity.AuditLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class auditLogDAO {
    public boolean insert(AuditLog log) {
        String sql = """
                INSERT INTO Audit_Log(
                    id_log, actor_id, actor_name, actor_role,
                    action_type, target_type, target_id, details, created_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ensureTableExists(conn);
            ps.setString(1, log.getIdLog());
            ps.setString(2, log.getActorId());
            ps.setString(3, log.getActorName());
            ps.setString(4, log.getActorRole());
            ps.setString(5, log.getActionType());
            ps.setString(6, log.getTargetType());
            ps.setString(7, log.getTargetId());
            ps.setString(8, log.getDetails());
            ps.setTimestamp(9, Timestamp.valueOf(log.getCreatedAt()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<AuditLog> getAll() {
        List<AuditLog> list = new ArrayList<>();
        String sql = """
                SELECT id_log, actor_id, actor_name, actor_role, action_type,
                       target_type, target_id, details, created_at
                FROM Audit_Log
                ORDER BY created_at DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = executeQueryWithTableEnsure(conn, ps)) {
            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setIdLog(rs.getString("id_log"));
                log.setActorId(rs.getString("actor_id"));
                log.setActorName(rs.getString("actor_name"));
                log.setActorRole(rs.getString("actor_role"));
                log.setActionType(rs.getString("action_type"));
                log.setTargetType(rs.getString("target_type"));
                log.setTargetId(rs.getString("target_id"));
                log.setDetails(rs.getString("details"));
                Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    log.setCreatedAt(createdAt.toLocalDateTime());
                }
                list.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ResultSet executeQueryWithTableEnsure(Connection conn, PreparedStatement ps) throws SQLException {
        ensureTableExists(conn);
        return ps.executeQuery();
    }

    private void ensureTableExists(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS Audit_Log(
                    id_log VARCHAR(15) PRIMARY KEY,
                    actor_id VARCHAR(15),
                    actor_name VARCHAR(50) NOT NULL,
                    actor_role VARCHAR(20),
                    action_type VARCHAR(50) NOT NULL,
                    target_type VARCHAR(30) NOT NULL,
                    target_id VARCHAR(30),
                    details VARCHAR(255),
                    created_at DATETIME NOT NULL
                )
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        }
    }
}
