
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<jsp:useBean id="person"
             type="lab.mvc.model.Person"
             scope="request" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Young Generation</title>
</head>
<body>
    <h2>You were born in 1990 or later</h2>

    <p>First name:
        <jsp:getProperty name="person" property="firstName" />
    </p>

    <p>Last name:
        <jsp:getProperty name="person" property="lastName" />
    </p>

    <p>Birth date:
        <jsp:getProperty name="person" property="birthDay" />
    </p>

    <p>Sex:
        <jsp:getProperty name="person" property="sex" />
    </p>

    <a href="index.html">Back to form</a>
</body>
</html>
