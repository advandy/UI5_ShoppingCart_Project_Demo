<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<br><br>
	<h4>Account Login</h4>
    <form action="bookServlet?method=checkAccount" method="post">
    	Account: <input type="text" name = "account" /><br><br>
		Password: <input type="password" name="pass"/><br><br>
		<div style="color:red">${error}</div>
		<input type="submit" value="Submit"/>
    </form>
	
	
</center>

</body>
</html>