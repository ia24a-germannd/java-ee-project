<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction History</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/transaction.css">
    <link rel="stylesheet" href="styles/animation.css">
</head>
<body>
<div class="transaction-container">
    <div class="transactions-container">
        <a href="dashboard" class="back-button">← Back to Dashboard</a>

        <div class="page-header">
            <h2>Transaction History</h2>
        </div>

        <div class="account-info">
            <h3>Account: ${accountNumber}</h3>
        </div>

        <c:if test="${empty transactions}">
            <div class="no-transactions">
                <p>No transactions found for this account.</p>
            </div>
        </c:if>

        <div class="stagger-animation">
            <c:forEach var="transaction" items="${transactions}" varStatus="status">
                <div class="transaction-item" style="animation-delay: ${status.index * 0.1}s">
                    <div class="transaction-date">
                        <fmt:formatDate value="${transaction.date}" pattern="MM/dd/yyyy HH:mm"/>
                    </div>
                    <div class="transaction-amount ${transaction.amount >= 0 ? 'positive' : 'negative'}">
                        <fmt:formatNumber value="${transaction.amount}" type="currency"/>
                    </div>
                    <div class="transaction-recipient">
                        Recipient: ${transaction.recipient}
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>