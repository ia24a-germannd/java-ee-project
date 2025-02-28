package org.example.ebanking;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int accountId;
    private float amount;
    private Timestamp date;
    private String recipient;
    private boolean flagged;

    // Constructor
    public Transaction(int transactionId, int accountId, float amount, Timestamp date, String recipient, boolean flagged) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.recipient = recipient;
        this.flagged = flagged;
    }

    // Default constructor
    public Transaction() {
    }

    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public boolean isFlagged() { return flagged; }
    public void setFlagged(boolean flagged) { this.flagged = flagged; }
}