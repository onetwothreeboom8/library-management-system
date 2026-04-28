package dao;

import model.Admin;
import model.Student;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User login(String username, String password) {
        String sql = "SELECT id, username, password, role FROM users WHERE username = ? AND password = ?";

        System.out.println("Trying login:");
        System.out.println("Username = [" + username + "]");
        System.out.println("Password = [" + password + "]");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Connected to database: " + conn.getCatalog());

            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("USER FOUND!");

                int id = rs.getInt("id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");

                if (role != null) {
                    role = role.trim();
                }

                if ("ADMIN".equalsIgnoreCase(role)) {
                    return new Admin(id, dbUsername, dbPassword);
                } else if ("STUDENT".equalsIgnoreCase(role)) {
                    return new Student(id, dbUsername, dbPassword);
                } else {
                    System.out.println("Unknown role: " + role);
                    return null;
                }
            } else {
                System.out.println("USER NOT FOUND");
            }

        } catch (SQLException e) {
            System.out.println("DATABASE ERROR:");
            e.printStackTrace();
        }

        return null;
    }

    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername().trim());
            stmt.setString(2, user.getPassword().trim());
            stmt.setString(3, user.getRole().trim());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("REGISTER ERROR:");
            e.printStackTrace();
            return false;
        }
    }
}