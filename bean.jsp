<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="person"
             class="isep.lab1.bean.Person"
             scope="request" />

<jsp:setProperty name="person" property="*" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Person Information</title>
</head>
<body>
    <h2>Submitted Person Information</h2>

    <table border="1" cellpadding="8">
        <tr>
            <th>Field</th>
            <th>Value</th>
        </tr>
        <tr>
            <td>First name</td>
            <td><jsp:getProperty name="person"
                                 property="firstName" /></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><jsp:getProperty name="person"
                                 property="lastName" /></td>
        </tr>
        <tr>
            <td>Birth date</td>
            <td><jsp:getProperty name="person"
                                 property="birthDay" /></td>
        </tr>
        <tr>
            <td>Sex</td>
            <td><jsp:getProperty name="person"
                                 property="sex" /></td>
        </tr>
    </table>

    <p><a href="form-jsp.html">Back to form</a></p>
</body>
</html>
