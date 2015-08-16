<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script id="sap-ui-bootstrap"
   src="resources/sap-ui-core.js"
   data-sap-ui-theme="sap_bluecrystal"
   data-sap-ui-libs="sap.ui.commons,sap.ui.unified,sap.ui.ux3,sap.m,sap.ui.table, sap.ui.layout">
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cash</title>
</head>
<body>
<br>

Hello ${sessionScope.account.accountHolder}, &nbsp &nbsp<a href="bookServlet?method=logOff">Change Account</a>
<br>
Account No.: ${sessionScope.account.accountNo }
<br>
Account Balance: ${sessionScope.account.accountBalance }
<br>
<a href="bookServlet?method=transactionView">Transaction Overview</a>
<br>

<br>
<a href="bookServlet?method=getBooks">Go to Shopping</a>
<br>

<center>
 <div> ${stockAlert}</div>
 <br><br>
 <div style="color:red">${warning}</div>
<c:if test="${sessionScope.shoppingCart.sum!=0}">

 <form action="bookServlet?method=pay" method="post">  
  By clicking pay, you will confirm the transaction of Amount ${sessionScope.shoppingCart.sum } <br><br>
  <input type="submit" value="pay"/>

  
  
  
 </form>
 </c:if> 
</center>

</body>
</html>