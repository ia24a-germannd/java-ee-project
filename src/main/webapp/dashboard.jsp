<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-Banking Dashboard</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/dashboard.css">
    <link rel="stylesheet" href="styles/animation.css">
</head>
<body>
<%String username = (String) session.getAttribute("username");%>

<div class="dashboard-container">
    <div class="welcome-header">
        <h1 class="welcome-text">Welcome, <%= username %>!</h1>
    </div>

    <div class="accounts-section">
        <h2>Your Accounts</h2>
        <div class="accounts-grid">

            <div class="accounts-grid">
                <c:if test="${empty accounts}">
                    <p>No accounts found</p>
                </c:if>

                <c:forEach var="account" items="${accounts}">
                    <div class="account-card">
                        <div class="account-header">
                            <h3>Type: ${account.accountType}</h3>
                            <span class="account-number">Number: ${account.accountNumber}</span>
                        </div>
                        <div class="account-balance">
                            <span class="balance-label">Available Balance</span>
                            <span class="balance-amount">
                    ${account.balance}
                </span>
                        </div>
                        <div class="account-actions">
                            <form action="makeChecks" method="get"  style="flex: 1;">
                                <input type="hidden" name="accountId" value="${account.accountId}">
                                <button type="submit" class="btn primary" style="width: 100%;">Make a Check</button>
                            </form>
                            <form action="transactions" method="get" style="flex: 1;">
                                <input type="hidden" name="accountId" value="${account.accountId}">
                                <button type="submit" class="btn secondary" style="width: 100%;">View Transactions</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
</div>
<form action="index.jsp">
    <button type="submit" class="logout-button">Logout</button>
</form>
</body>
</html>