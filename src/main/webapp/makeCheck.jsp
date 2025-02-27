<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Make a Check</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/check.css">
    <link rel="stylesheet" href="styles/animation.css">
</head>
<body>
<div class="check-container">
    <div class="check-form-container">
        <a href="dashboard" class="back-button">← Back to Dashboard</a>
        <h2>Make a Check</h2>
        <form action="makeChecks" method="post" enctype="multipart/form-data">
            <input type="hidden" name="accountId" value="${param.accountId}">
            
            <div class="form-group">
                <label for="recipientName">Recipient Name:</label>
                <input type="text" id="recipientName" name="recipientName" required>
            </div>

            <div class="form-group">
                <label for="amount">Amount in Numbers ($):</label>
                <input type="number" id="amount" name="amount" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="amountInWords">Amount in Words:</label>
                <input type="text" id="amountInWords" name="amountInWords" required>
            </div>

            <div class="form-group">
                <label for="note">Note/Memo:</label>
                <textarea id="note" name="note" rows="3"></textarea>
            </div>

            <div class="form-group">
                <label for="signature">Upload Signature:</label>
                <input type="file" id="signature" name="signature" accept="image/*" required>
            </div>

            <button type="submit" class="submit-btn">Create Check</button>
        </form>
    </div>
</div>
</body>
</html>