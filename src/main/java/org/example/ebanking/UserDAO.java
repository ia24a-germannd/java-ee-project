package org.example.ebanking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public int getUserId(String username, String password) {
        String sql = "SELECT user_id FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean userHasAccount(int userId) {
        String sql = "SELECT account_id FROM Accounts WHERE user_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAccount(int userId, String accountNumber) {
        String sql = "INSERT INTO Accounts (user_id, account_number, balance) VALUES (?, ?, 1000.00)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int registerUser(String username, String password, String email) {
        String sql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnector.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        conn.commit();
                        return userId;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}