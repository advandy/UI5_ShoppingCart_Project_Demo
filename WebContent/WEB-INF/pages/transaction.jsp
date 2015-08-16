<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script id="sap-ui-bootstrap"
   src="resources/sap-ui-core.js"
   data-sap-ui-theme="sap_bluecrystal"
   data-sap-ui-libs="sap.ui.commons,sap.ui.unified,sap.ui.ux3,sap.m,sap.ui.table, sap.ui.layout">
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="customControls/sap.ui.custom.order.js"></script>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>Your Transaction</title>
</head>
<body>
<br>
  <div class="transactionView"></div> 

 <style>
 
        .itemImage {margin:10px; height:50px; width:50px; float: left;}
        .itemContainer {float: left; margin-right: 40px}
        .itemTitle {margin:10px;}
        .itemDescription {margin:10px;}
        .itemPrice {float: left; margin:10px; margin-right: 30px}
        .itemNum {float: left; margin:10px;}
        .itemClaim{float:left; margin:10px;}
        .orderSum{margin-top: 10px;}
        .confirmReceive {margin-top:10px;}

</style>
 
 
 <script>
	var obj = ${transactionData}
 	for (var i=0;i<obj.length;i++){
 		
 		var oOrder = new order({
 		    number: obj[i].items.length,
 		    vendorName:"Andy Cheng",
 		    orderDate:"",
 		    vendorID:"Yunhancheng.altervista.org",
 		    orderID: obj[i].tr_time,
 		    sum: obj[i].tr_sum,
 		    buttonCheck: new sap.ui.commons.Button({text: "received"})
 		});
 		
 		
 		
 		
 		for(var j=0;j<obj[i].items.length;j++){
 			
 			oOrder.addItem(new CustListItem({
 				article: obj[i].items[j].split(",")[0],
 				num:  obj[i].items[j].split(",")[1],
 				additionalText:"xxxx this is the description of '" + obj[i].items[j].split(",")[0] +"'. Bla bla bla...." ,
 				price: ""
 			}));
 			
 		
 		}
 		
 		oOrder. placeAt("trans");
 		
 	}
 	
 	
 	
 	 	
 	
 </script>
 <br><br>
 
 <div id="trans"></div>
 

</body>
</html>