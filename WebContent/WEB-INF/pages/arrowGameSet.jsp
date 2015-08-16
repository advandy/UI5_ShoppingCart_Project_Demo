<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Game Set</title>
</head>
<body>
<br>
<br>
<center>
	<form action="arrowServlet?method=initiateGame" id="gameSet" method="post">
		Game Type: 
		<select name="gameType" form="gameSet">
		  <option value="301">301</option>
		  <option value="501">501</option>
		  <option value="701">701</option>
		  <option value="1001">1001</option>
		</select>	
		
		<br><br>
		
				
		In Option: <input type="radio" name="inOption" value="openIn" checked>Open In  
				   <input type="radio" name="inOption" value="doubleIn">Double In
				   <br><br>
		Out Option: <input type="radio" name="outOption" value="openOut" checked>Open Out  
				   <input type="radio" name="outOption" value="doubleOut" >Double Out
				   <br><br>
	 Player 1:	<input type="text" name="p1" placeholder = "player 1"><br><br>
		Player 2: <input type="text" name="p2" placeholder = "player 2"><br><br>
		<input type="submit" value="Start">
	</form>
</center>
</body>
</html>