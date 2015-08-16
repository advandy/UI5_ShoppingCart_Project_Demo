<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${gameType} Game</title>
</head>
<body>
<center>
	<table cellPadding=20>
	 <tr>
	 	<td></td>
	 	<td>${p1} </td>
	 	<td>${p2}</td>
	 </tr>
	 
	 <tr>
	  <td></td>
	  <td id="p1Score">${gameType}</td>
	  <td  id="p2Score">${gameType}</td>	 
	 </tr>
	 
	  <tr>
	  <td></td>
	  <td id="p1Pre"></td>
	  <td  id="p2Pre"></td>	 
	 </tr>
	 
	 <tr class="r1">
	 	<td id="round"> Round 1: </td>
	 	<td >
	 		<form id="r1p1" onSubmit="return finish(this)">
	 		    <input type="hidden" name="round" value="1">
	 		    <input type="hidden" name="player" value="1">
	 			<input name="first" value=0 size=1>*<select name="firstMulti"form="r1p1"><option>1</option><option>2</option><option>3</option></select><br>
	 			<input name="second" value=0 size=1>*<select name="secondMulti"><option>1</option><option>2</option><option>3</option></select><br>
	 			<input name="third" value=0 size=1>*<select name="thirdMulti"><option>1</option><option>2</option><option>3</option></select> 
	 			<input type="submit" value="finish">
	 		</form>
	 	</td>
	 	<td>
	 	<form id="r1p2" onSubmit="return finish(this)">
	 		    <input type="hidden" name="round" value="1">
	 		    <input type="hidden" name="player" value="2">
	 			<input name="first" value=0 size=1>*<select name="firstMulti"form="r1p1"><option>1</option><option>2</option><option>3</option></select><br>
	 			<input name="second" value=0 size=1>*<select name="secondMulti"><option>1</option><option>2</option><option>3</option></select><br>
	 			<input name="third" value=0 size=1>*<select name="thirdMulti"><option>1</option><option>2</option><option>3</option></select> 
	 			<input  type="submit" value="finish">
	 		</form>
	 	</td>
	 </tr>
	 
	
	 
	</table>

</center>

<script>
 var overflowp1=0;
 var overflowp2=0;
 var round = 1;
 var r1 = 1;
 var r2 = 1;
 var p1Score = parseInt("${gameType}") ;
 var p2Score =  parseInt("${gameType}") ;
 
 
 function finish(node){
	 $node = $(node);	 
	 var iRound = parseInt($node.find("[name=round]").val());
	 var iPlayer = parseInt($node.find("[name=player]").val());
	 
	 var iThrow1 = parseInt($node.find("[name=first]").val());
	 var iThrow1Multiple = parseInt($node.find("[name=firstMulti]").val());
	 
	 var iThrow2 = parseInt($node.find("[name=second]").val());
	 var iThrow2Multiple = parseInt($node.find("[name=secondMulti]").val());
	 
	 var iThrow3 = parseInt($node.find("[name=third]").val());
	 var iThrow3Multiple = parseInt($node.find("[name=thirdMulti]").val());
	 
	 
		 if("${inOption}"=="openIn"){
			 if(iPlayer==1){
				 p1Score = p1Score -(iThrow1*iThrow1Multiple+iThrow2*iThrow2Multiple+iThrow3*iThrow3Multiple);
				 if(p1Score>=0){
					 $("#p1Score").html(p1Score);
					 $("#p1Pre").append(iThrow1+"*"+iThrow1Multiple+"<br>");
					 $("#p1Pre").append(iThrow2+"*"+iThrow2Multiple+"<br>");
					 $("#p1Pre").append(iThrow3+"*"+iThrow3Multiple+"<br><br>");
					 r1++;
					 $node.find("[name=round]").val(r1);					 					 
				 }else{
					 alert("OverFlow! Player"+iPlayer);
					 p1Score = p1Score +(iThrow1*iThrow1Multiple+iThrow2*iThrow2Multiple+iThrow3*iThrow3Multiple);
					 overflowp1++;
					 
				 }
				 
				 if(p1Score==0){
					 alert("Player "+iPlayer+" won!");
					 return false;
				 }
				 
			 }else{
				 p2Score = p2Score -(iThrow1*iThrow1Multiple+iThrow2*iThrow2Multiple+iThrow3*iThrow3Multiple);
				 if(p2Score>=0){
					 $("#p2Score").html(p2Score);
					 $("#p2Pre").append(iThrow1+"*"+iThrow1Multiple+"<br>");
					 $("#p2Pre").append(iThrow2+"*"+iThrow2Multiple+"<br>");
					 $("#p2Pre").append(iThrow3+"*"+iThrow3Multiple+"<br><br>");
					 r2++;
					 $node.find("[name=round]").val(r2);
				 }else{
					 alert("OverFlow! Player"+iPlayer);
					 overflowp2++;
					 p2Score = p2Score +(iThrow1*iThrow1Multiple+iThrow2*iThrow2Multiple+iThrow3*iThrow3Multiple);
				 }
				 
				 if(p2Score==0){
					 alert("Player "+iPlayer+" won!");
					 return false;
				 }
			 }
			 if(r1==r2){
				 round = r1;
				 $("#round").html("Round "+round+":");
				 
			 }			 
			 
			 
		 }
	  
	 
	 
	 return false;
 }
 

</script>

</body>
</html>