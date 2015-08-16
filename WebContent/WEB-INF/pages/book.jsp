<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book- ${book.name}</title>
</head>
<body>
<center>

 <br><br>
 Title: ${book.name } <br><br>
 Author: ${book.author } <br><br>
 Price: ${book.price } (UVP: ${book.originPrice}) <br><br>
 Publishing Date: ${book.date } <br><br>
 Stock: ${book.stock } <br><br>
 
 <form name="continue"action="bookServlet?method=getBooks" method="post">
    <input type="hidden" name="minPrice" value="${minPrice }"/>
    <input type="hidden" name = "maxPrice" value="${maxPrice }"/>
    <input type="hidden" name="pageNo" value = "${pageNo }"/>
    <input type="hidden" name="keyword" value="${keyword }"/>
    <A HREF="javascript:document.continue.submit()">Continue Shopping</A>
    </form>
</center>


</body>
</html>