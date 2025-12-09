package com.health.dao;

import java.sql.*;
import java.util.*;

public class SymptomDAO {
    public List<Map<String,Object>> findAll() throws SQLException {
        List<Map<String,Object>> list = new ArrayList<>();
        String sql = "SELECT id, name FROM symptoms ORDER BY name";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("id", rs.getInt(1));
                row.put("name", rs.getString(2));
                list.add(row);
            }
        }
        return list;
    }
}
