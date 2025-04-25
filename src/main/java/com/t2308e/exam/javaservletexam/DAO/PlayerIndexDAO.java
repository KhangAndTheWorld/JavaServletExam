package com.t2308e.exam.javaservletexam.DAO;
import com.t2308e.exam.javaservletexam.entity.PlayerIndex;
import com.t2308e.exam.javaservletexam.helper.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerIndexDAO {
    public void addPlayerIndex(PlayerIndex playerIndex) throws SQLException {
        String sql1 = "SELECT index_id FROM indexer WHERE name = ?";
        int indexId;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql1)) {
            stmt.setString(1, playerIndex.getIndexName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                indexId = rs.getInt("index_id");
            } else {
                throw new SQLException("Index not found for name: " + playerIndex.getIndexName());
            }
        }

        String sql2 = "INSERT INTO player (name, full_name, age, index_id) VALUES (?, ?, ?, ?)";
        int playerId;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, playerIndex.getPlayerName());
            stmt.setString(2, playerIndex.getFullName());
            stmt.setInt(3, playerIndex.getPlayerAge());
            stmt.setInt(4, indexId); // Gán giá trị index_id
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                playerId = rs.getInt(1);
            } else {
                throw new SQLException("Failed to get player ID.");
            }
        }

        String sql3 = "INSERT INTO player_index (player_id, index_id, value) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql3)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, indexId);
            stmt.setFloat(3, playerIndex.getValue());
            stmt.executeUpdate();
        }
    }

    public List<PlayerIndex> getAllPlayerIndexes() throws SQLException {
        List<PlayerIndex> playerIndexes = new ArrayList<>();
        String sql = "SELECT pi.id, p.name, p.full_name, p.age, i.name AS index_name, pi.value " +
                "FROM player_index pi " +
                "JOIN player p ON pi.player_id = p.player_id " +
                "JOIN indexer i ON pi.index_id = i.index_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PlayerIndex pi = new PlayerIndex();
                pi.setId(rs.getInt("id"));
                pi.setPlayerName(rs.getString("name"));
                pi.setFullName(rs.getString("full_name"));
                pi.setPlayerAge(rs.getInt("age"));
                pi.setIndexName(rs.getString("index_name"));
                pi.setValue(rs.getFloat("value"));
                playerIndexes.add(pi);
            }
        }
        return playerIndexes;
    }

    public PlayerIndex getPlayerIndexById(int id) throws SQLException {
        String sql = "SELECT pi.id, p.name, p.full_name, p.age, i.name AS index_name, pi.value " +
                "FROM player_index pi " +
                "JOIN player p ON pi.player_id = p.player_id " +
                "JOIN indexer i ON pi.index_id = i.index_id " +
                "WHERE pi.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PlayerIndex pi = new PlayerIndex();
                pi.setId(rs.getInt("id"));
                pi.setPlayerName(rs.getString("name"));
                pi.setFullName(rs.getString("full_name"));
                pi.setPlayerAge(rs.getInt("age"));
                pi.setIndexName(rs.getString("index_name"));
                pi.setValue(rs.getFloat("value"));
                return pi;
            }
        }
        return null;
    }

    public void updatePlayerIndex(PlayerIndex playerIndex) throws SQLException {
        String sql = "UPDATE player p " +
                "JOIN player_index pi ON p.player_id = pi.player_id " +
                "SET p.name = ?, p.age = ?, pi.value = ? " +
                "WHERE pi.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerIndex.getPlayerName());
            stmt.setInt(2, playerIndex.getPlayerAge());
            stmt.setFloat(3, playerIndex.getValue());
            stmt.setInt(4, playerIndex.getId());
            stmt.executeUpdate();
        }
    }

    public void deletePlayerIndex(int id) throws SQLException {
        String sql = "DELETE FROM player_index WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
