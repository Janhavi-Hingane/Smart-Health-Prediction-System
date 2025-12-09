package com.health.dao;

import com.health.model.User;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, email, password_hash, role FROM users WHERE email=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    User u = new User();
                    u.setId(rs.getInt(1));
                    u.setName(rs.getString(2));
                    u.setEmail(rs.getString(3));
                    u.setPasswordHash(rs.getString(4));
                    u.setRole(rs.getString(5));
                    return u;
                }
            }
        }
        return null;
    }

    public User findById(int id) throws SQLException {
        String sql = "SELECT id, name, email, password_hash, role FROM users WHERE id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    User u = new User();
                    u.setId(rs.getInt(1));
                    u.setName(rs.getString(2));
                    u.setEmail(rs.getString(3));
                    u.setPasswordHash(rs.getString(4));
                    u.setRole(rs.getString(5));
                    return u;
                }
            }
        }
        return null;
    }

    public boolean create(String name, String email, String rawPassword, String role) throws SQLException {
        String hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        String sql = "INSERT INTO users(name, email, password_hash, role) VALUES(?,?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hash);
            ps.setString(4, role);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean checkPassword(User u, String rawPassword){
        return org.mindrot.jbcrypt.BCrypt.checkpw(rawPassword, u.getPasswordHash());
    }
}
