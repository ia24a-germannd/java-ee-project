package org.example.ebanking;

import java.math.BigDecimal;
import java.sql.Date;

public class Check {
    private int checkId;
    private int accountId;
    private String recipientName;
    private float amount;
    private Date checkDate;
    private String note;
    private String signatureFile;

    public Check() {}

    public Check(int checkId, int accountId, String recipientName, float amount,
                Date checkDate, String note, String signatureFile) {
        this.checkId = checkId;
        this.accountId = accountId;
        this.recipientName = recipientName;
        this.amount = amount;
        this.checkDate = checkDate;
        this.note = note;
        this.signatureFile = signatureFile;
    }

    // Getters and Setters
    public int getCheckId() { return checkId; }
    public void setCheckId(int checkId) { this.checkId = checkId; }
    
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    
    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount; }
    
    public Date getCheckDate() { return checkDate; }
    public void setCheckDate(Date checkDate) { this.checkDate = checkDate; }
    
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
    public String getSignatureFile() { return signatureFile; }
    public void setSignatureFile(String signatureFile) { this.signatureFile = signatureFile; }
}