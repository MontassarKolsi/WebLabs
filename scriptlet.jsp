<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Scriptlet</title>
</head>
<body>
    <h1>Server Information</h1>

    <%
        java.util.Date currentTime = new java.util.Date();
        String clientIP = request.getRemoteHost();
        String serverIP = request.getLocalAddr();
    %>

    <p>Current time: <%= currentTime %></p>
    <p>client IP address: <%= clientIP %></p>
    <p>Server IP address: <%= serverIP %></p>
</body>
</html>
