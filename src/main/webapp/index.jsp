<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Chase E-Banking</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/form.css">
    <link rel="stylesheet" href="styles/animation.css">
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
    <h1>Login</h1>
    <form action="LoginServlet" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <div class="remember-me">
            <input type="checkbox" name="rememberMe" id="rememberMe">
            <label for="rememberMe">Remember Me</label>
        </div>
        <input type="submit" value="Login">
    </form>
    <p>Don't have an account? <a href="register.jsp">Sign up</a></p>
</div>

</body>
</html>