package com.health.dao;

import com.health.model.Disease;
import java.sql.*;
import java.util.*;

public class DiseaseDAO {

    public List<Disease> findAll() throws SQLException {
        List<Disease> list = new ArrayList<>();
        String sql = "SELECT id, name, precaution, medicine FROM diseases ORDER BY name";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Disease d = new Disease(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                list.add(d);
            }
        }
        return list;
    }

    public int create(String name, String precaution, String medicine) throws SQLException {
        String sql = "INSERT INTO diseases(name, precaution, medicine) VALUES(?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, name);
            ps.setString(2, precaution);
            ps.setString(3, medicine);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public void update(int id, String name, String precaution, String medicine) throws SQLException {
        String sql = "UPDATE diseases SET name=?, precaution=?, medicine=? WHERE id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, precaution);
            ps.setString(3, medicine);
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection con = DBUtil.getConnection()){
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM disease_symptom WHERE disease_id=?")){
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM diseases WHERE id=?")){
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
    }

    public void setSymptoms(int diseaseId, List<Integer> symptomIds) throws SQLException {
        try (Connection con = DBUtil.getConnection()){
            con.setAutoCommit(false);
            try (PreparedStatement del = con.prepareStatement("DELETE FROM disease_symptom WHERE disease_id=?")){
                del.setInt(1, diseaseId);
                del.executeUpdate();
            }
            try (PreparedStatement ins = con.prepareStatement("INSERT INTO disease_symptom(disease_id, symptom_id) VALUES(?,?)")){
                for(Integer sid : symptomIds){
                    ins.setInt(1, diseaseId);
                    ins.setInt(2, sid);
                    ins.addBatch();
                }
                ins.executeBatch();
            }
            con.commit();
        }
    }

    public Map<Integer, List<Integer>> getDiseaseSymptomMap() throws SQLException {
        Map<Integer, List<Integer>> map = new HashMap<>();
        String sql = "SELECT disease_id, symptom_id FROM disease_symptom";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                map.computeIfAbsent(rs.getInt(1), k -> new ArrayList<>()).add(rs.getInt(2));
            }
        }
        return map;
    }
}
