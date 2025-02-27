package org.example.ebanking;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckDAO {
    private DBConnector dbConnector = new DBConnector();

    public boolean createCheck(Check check) {
        String sql = "INSERT INTO Checks (account_id, recipient_name, amount, check_date, note, signature_file) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("SQL Query: " + sql);
        System.out.println("Attempting to insert check with:");
        System.out.println("Account ID: " + check.getAccountId());
        System.out.println("Recipient: " + check.getRecipientName());
        System.out.println("Amount: " + check.getAmount());
        System.out.println("Date: " + check.getCheckDate());
        System.out.println("Note: " + check.getNote());
        System.out.println("File: " + check.getSignatureFile());

        boolean success = dbConnector.executeUpdate(sql,
                check.getAccountId(),
                check.getRecipientName(),
                check.getAmount(),
                check.getCheckDate(),
                check.getNote(),
                check.getSignatureFile()) > 0;

        if (success) {
            try {
                String verifySQL = "SELECT * FROM Checks WHERE account_id = ? ORDER BY check_id DESC LIMIT 1";
                ResultSet rs = dbConnector.executeQuery(verifySQL, check.getAccountId());
                if (rs.next()) {
                    System.out.println("Check successfully created with ID: " + rs.getInt("check_id"));
                }
            } catch (SQLException e) {
                System.out.println("Error verifying check creation: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to create check record");
        }

        return success;
    }

    public List<Check> getChecksByAccountId(int accountId) {
        List<Check> checks = new ArrayList<>();
        String sql = "SELECT * FROM Checks WHERE account_id = ? ORDER BY check_date DESC";
        
        try {
            ResultSet rs = dbConnector.executeQuery(sql, accountId);
            while (rs.next()) {
                Check check = new Check(
                    rs.getInt("check_id"),
                    rs.getInt("account_id"),
                    rs.getString("recipient_name"),
                    rs.getFloat("amount"),
                    rs.getDate("check_date"),
                    rs.getString("note"),
                    rs.getString("signature_file")
                );
                checks.add(check);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }

    public Check getCheckById(int checkId) {
        String sql = "SELECT * FROM Checks WHERE check_id = ?";
        
        try {
            ResultSet rs = dbConnector.executeQuery(sql, checkId);
            if (rs.next()) {
                return new Check(
                    rs.getInt("check_id"),
                    rs.getInt("account_id"),
                    rs.getString("recipient_name"),
                    rs.getFloat("amount"),
                    rs.getDate("check_date"),
                    rs.getString("note"),
                    rs.getString("signature_file")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Check> getRecentChecks(int accountId, int limit) {
        List<Check> checks = new ArrayList<>();
        String sql = "SELECT * FROM Checks WHERE account_id = ? ORDER BY check_date DESC LIMIT ?";
        
        try {
            ResultSet rs = dbConnector.executeQuery(sql, accountId, limit);
            while (rs.next()) {
                Check check = new Check(
                    rs.getInt("check_id"),
                    rs.getInt("account_id"),
                    rs.getString("recipient_name"),
                    rs.getFloat("amount"),
                    rs.getDate("check_date"),
                    rs.getString("note"),
                    rs.getString("signature_file")
                );
                checks.add(check);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }
}