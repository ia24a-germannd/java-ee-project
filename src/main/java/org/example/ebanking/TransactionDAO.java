package org.example.ebanking;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private DBConnector dbConnector = new DBConnector();

    public boolean createTransaction(int accountId, BigDecimal amount, String recipient) {
        String sql = "INSERT INTO Transactions (account_id, amount, recipient) VALUES (?, ?, ?)";
        return dbConnector.executeUpdate(sql, accountId, amount, recipient) > 0;
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE account_id = ? ORDER BY date DESC";
        
        try {
            ResultSet rs = dbConnector.executeQuery(sql, accountId);
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getInt("account_id"),
                    rs.getBigDecimal("amount"),
                    rs.getTimestamp("date"),
                    rs.getString("recipient"),
                    rs.getBoolean("flagged")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean flagTransaction(int transactionId) {
        String sql = "UPDATE Transactions SET flagged = TRUE WHERE transaction_id = ?";
        return dbConnector.executeUpdate(sql, transactionId) > 0;
    }

    public List<Transaction> getRecentTransactions(int accountId, int limit) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE account_id = ? ORDER BY date DESC LIMIT ?";
        
        try {
            ResultSet rs = dbConnector.executeQuery(sql, accountId, limit);
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("transaction_id"),
                    rs.getInt("account_id"),
                    rs.getBigDecimal("amount"),
                    rs.getTimestamp("date"),
                    rs.getString("recipient"),
                    rs.getBoolean("flagged")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}