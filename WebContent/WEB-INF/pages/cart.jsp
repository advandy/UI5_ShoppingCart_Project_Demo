<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script id="sap-ui-bootstrap"
   src="resources/sap-ui-core.js"
   data-sap-ui-theme="sap_bluecrystal"
   data-sap-ui-libs="sap.ui.commons,sap.ui.unified,sap.ui.ux3,sap.m,sap.ui.table, sap.ui.layout">
</script>
<script src="customControls/sap.ui.custom.counter.js"></script>

</head>
<body>
  
   <br><br>
   
   <script>
    var oCartLabel = new sap.ui.commons.Label({
    	text: "You Shopping Cart contains: ${sessionScope.shoppingCart.bookNumber} item(s)"
    });
    
    oCartLabel.placeAt("titleQuantity");
   		
   </script>
   
   <div id="titleQuantity"></div>
   <br><br>
   
   <script>
       var aCartData=[];
   
	   <c:forEach items="${sessionScope.shoppingCart.items}" var="item">
	   		var oItem = {};
	   		oItem.name = "${item.book.name}";
	   		oItem.id = "${item.book.id}";
	   		oItem.quantity = ${item.quantity};
	   		oItem.price = ${item.book.price};
	   		oItem.itemPrice = ${item.itemPrice};
	   		oItem.deleteHref = "bookServlet?method=deleteItem&itemId=${item.book.id}&pageNo=${pageNo}&minPrice=${minPrice}&maxPrice=${maxPrice}";
	   		aCartData.push(oItem);
	   </c:forEach>  
	        var oItem = {}
	        oItem.id="";
	        oItem.name="SUMMARY";
	        oItem.quantity=${sessionScope.shoppingCart.bookNumber};
	        oItem.price="";
	        oItem.itemPrice= ${sessionScope.shoppingCart.sum };
	        oItem.deleteHref="bookServlet?method=clearCart&pageNo=${pageNo}&minPrice=${minPrice}&maxPrice=${maxPrice}";
	        aCartData.push(oItem);
	  
	   
	   var oCartTable = new sap.ui.table.Table({
			title: "Your Shopping Cart:  Quantity: ${sessionScope.shoppingCart.bookNumber}",
			visibleRowCount: aCartData.length,
			firstVisibleRow: aCartData.length		
		});
	   
	   oCartTable.addColumn(new sap.ui.table.Column({
			label: new sap.ui.commons.Label({text: "Item"}),
			template: new sap.ui.commons.Label().bindProperty("text", "name"),
			sortProperty: "text",
			filterProperty: "text",
			width: "75px",
			hAlign: "Center"
		}));
	   
	   //custom counter control;
	   
	   counter.extend("cartCounter",{
		   metadata:{
			   properties:{
				  "itemId":"string",
				  "itemName":"string"
			   }
		   },
		   
		   renderer: {}
	   });
	   
	   
	   oCartTable.addColumn(new sap.ui.table.Column({
			label: new sap.ui.commons.Label({text: "Quantity"}),
			template: new cartCounter({change: function(){
				
				
				var itemId = this.getItemId();
				var quantity = this.getValue();
				if (quantity==0){
					window.location.href = "bookServlet?method=deleteItem&itemId="+itemId+"&pageNo=${pageNo}&minPrice=${minPrice}&maxPrice=${maxPrice}";
				}else{
					
					var url = "bookServlet";
				    
					  var args = {"method":"updateItemQuantity","id":itemId,"quantity":quantity,"time":new Date()};
					  
				  
					  $.post(url,args,function(data){						  
						  window.location.reload();			 						  
					  },"JSON");
				}				
			}}).bindProperty("value", "quantity").bindProperty("itemId","id").bindProperty("itemName","name"),
			sortProperty: "value",
			filterProperty: "value",
			width: "75px",
			hAlign: "Center"
		}));
	   
	   oCartTable.addColumn(new sap.ui.table.Column({
			label: new sap.ui.commons.Label({text: "Price"}),
			template: new sap.ui.commons.Label().bindProperty("text", "price"),
			sortProperty: "text",
			filterProperty: "text",
			width: "75px",
			hAlign: "Center"
		}));
	   
	   oCartTable.addColumn(new sap.ui.table.Column({
			label: new sap.ui.commons.Label({text: "Total"}),
			template: new sap.ui.commons.Label().bindProperty("text", "itemPrice"),
			sortProperty: "text",
			filterProperty: "text",
			width: "75px",
			hAlign: "Center"
		}));
	   
	   oCartTable.addColumn(new sap.ui.table.Column({			
			template: new sap.ui.commons.Link({text:"Delete"}).bindProperty("href", "deleteHref"),			
			width: "30px"
			
		}));
	   
	   var oModel = new sap.ui.model.json.JSONModel();
	   oModel.setData({data: aCartData});
	   oCartTable.setModel(oModel);
	   oCartTable.bindRows("/data");
	   
	   oCartTable.placeAt("shoppingCartTable");
   		
   
   </script>
   
   <div id="shoppingCartTable"></div>
 
   
 
     <script>
  		var lToShopping = new sap.ui.commons.Link("toShopping",{
  			text: "Continue Shopping",
  			href: "bookServlet?method=getBooks&keyword=${keyword}&pageNo=${pageNo}&minPrice=${minPrice}&maxPrice=${maxPrice}"
  		});
  		
  		var lClearCart = new sap.ui.commons.Link("clearCart",{
  			text: "Empty Shopping Cart",
  			href: "bookServlet?method=clearCart&keyword=${keyword}&pageNo=${pageNo}&minPrice=${minPrice}&maxPrice=${maxPrice}",
  		});
  		
  		var lCash = new sap.ui.commons.Link("toCash",{
  			text: "Go Cash!",
  			href: "bookServlet?method=toCashPage"
  		})
  		
  		  		
  		var oCartOperationLayout = new sap.ui.layout.HorizontalLayout({
  			content: [lToShopping, lClearCart, lCash]
  		});
  		
  		oCartOperationLayout.placeAt("opLayout");
  		
  </script>
  <style>
  		#toShopping {margin-right: 40px;}
  		#clearCart {margin-right: 40px;}
  		#toCash {margin-right: 30px;}
  		
  </style>
  <br><br>
  <div id="opLayout" style="width:30%; margin-left:auto;margin-right:auto"></div>
</body>
</html>