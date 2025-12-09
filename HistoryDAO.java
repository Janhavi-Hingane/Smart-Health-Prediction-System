package com.health.dao;

import java.sql.*;
import java.util.*;

public class HistoryDAO {
    public void insert(int userId, String inputText, Integer diseaseId) throws SQLException {
        String sql = "INSERT INTO history(user_id, input_text, predicted_disease_id, created_at) VALUES(?,?,?,NOW())";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, userId);
            ps.setString(2, inputText);
            if(diseaseId == null) ps.setNull(3, java.sql.Types.INTEGER);
            else ps.setInt(3, diseaseId);
            ps.executeUpdate();
        }
    }

    public List<Map<String,Object>> findByUser(int userId) throws SQLException {
        String sql = "SELECT h.id, h.input_text, d.name, h.created_at FROM history h LEFT JOIN diseases d ON h.predicted_disease_id = d.id WHERE h.user_id=? ORDER BY h.created_at DESC";
        List<Map<String,Object>> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Map<String,Object> row = new HashMap<>();
                    row.put("id", rs.getInt(1));
                    row.put("input", rs.getString(2));
                    row.put("prediction", rs.getString(3));
                    row.put("created_at", rs.getTimestamp(4));
                    list.add(row);
                }
            }
        }
        return list;
    }
}
