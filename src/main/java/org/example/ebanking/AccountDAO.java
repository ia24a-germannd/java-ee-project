package org.example.ebanking;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private DBConnector dbConnector = new DBConnector();

    public List<Account> getAccountsByUserId(int userId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Accounts WHERE user_id = ?";
        
        try {
            ResultSet rs = dbConnector.executeQuery(sql, userId);
            while (rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getInt("user_id"),
                    rs.getString("account_number"),
                    rs.getFloat("balance"),
                    rs.getString("account_type")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean createAccount(int userId, String accountNumber, String accountType) {
        String sql = "INSERT INTO Accounts (user_id, account_number, account_type) VALUES (?, ?, ?)";
        return dbConnector.executeUpdate(sql, userId, accountNumber, accountType) > 0;
    }

    public float getBalance(int accountId) {
        String sql = "SELECT balance FROM Accounts WHERE account_id = ?";
        try {
            ResultSet rs = dbConnector.executeQuery(sql, accountId);
            if (rs.next()) {
                return rs.getFloat("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    public boolean updateBalance(Account account) {
        String sql = "UPDATE Accounts SET balance = ? WHERE account_id = ?";

        System.out.println("Updating balance for account " + account.getAccountId() +
                " to " + account.getBalance());

        return dbConnector.executeUpdate(sql,
                account.getBalance(),
                account.getAccountId()) > 0;
    }

    public Account getAccount(int accountId) {
        String sql = "SELECT * FROM Accounts WHERE account_id = ?";
        try {
            ResultSet rs = dbConnector.executeQuery(sql, accountId);
            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setBalance(rs.getFloat("balance"));
                account.setAccountType(rs.getString("account_type"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int getAccountIdByUserId(int userId) {
        String sql = "SELECT account_id FROM Accounts WHERE user_id = ?";
        try {
            ResultSet rs = dbConnector.executeQuery(sql, userId);
            if (rs.next()) {
                return rs.getInt("account_id");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account_id: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
}