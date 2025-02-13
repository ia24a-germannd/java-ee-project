<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Chase E-Banking</title>
    <link rel="stylesheet" href="styles/style.css">
    <style>
        body {
            background: url('./resources/backdrop_login.png') no-repeat center center fixed;
            background-size: cover;
        }
    </style>
</head>
<body>
<div class="container">
    <img src="resources/chase-bank.png" alt="Chase Bank Logo" class="logo">
    <h1>Register</h1>
    <form action="RegisterServlet" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="password" name="confirm_password" placeholder="Confirm Password" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="submit" value="Sign Up">
    </form>
    <p>Already have an account? <a href="index.jsp">Login here</a></p>
</div>
</body>
</html>