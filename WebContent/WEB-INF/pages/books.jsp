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
<script src="customControls/sap.ui.custom.itemLayout.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<script>
		var oHeader = new sap.ui.commons.ApplicationHeader("appHeader",{
		    logoSrc: "http://www.sap.com/global/images/SAPLogo.gif",
		    logoText: "Shopping Web Application Demo",
		    displayLogoff: true,
		    userName: "Guest",
		    displayWelcome: false,
		    layoutData : new sap.ui.layout.GridData({
		            span : "L8 M8 S8"
		        }),
		    logoff: function(){
		    	window.location.href="bookServlet?method=toCashPage";
		    }     
		});
		
		if("${sessionScope.account}"){
			oHeader.setUserName("${sessionScope.account.accountHolder}");
			oHeader.setDisplayWelcome(true);
		}
		
		oHeader.placeAt("header");
		
		
</script>

 <div id="header" style="width:62%; margin-left:auto; margin-right:auto"></div>
 
 

 



  <script>
   var oLastEntry = new sap.ui.commons.Label({text:"Last item added: ${param.name}", design: sap.ui.commons.LabelDesign.Bold})
   
   if("${param.name}"!=""){
	   oLastEntry.placeAt("lastEntry");
   }
  </script>
  <div id="lastEntry"></div>
  <br><br>
  <style>
  #lastEntry{position: absolute; right:20%}
  </style>
  
  <script>
  	var oShoppingCartText = new sap.ui.commons.Label({text:"Your shopping Cart contains "+ "${sessionScope.shoppingCart.bookNumber}"+" items. "});
  	var shoppingCartLink = new sap.ui.commons.Link("cartLink",{text:" Go to Cart", href:"bookServlet?method=toCartPage&pageNo=${param.pageNo}&minPrice=${param.minPrice}&maxPrice=${param.maxPrice}&keyword=${keyword}"});
  	
  	var oGoToCartLayout = new sap.ui.layout.HorizontalLayout("goToCartLayout",{
			content: [oShoppingCartText, shoppingCartLink]
		});
  	
  	
  	if("${sessionScope.shoppingCart }"!=""){
  		oGoToCartLayout.placeAt("shoppingCartReminder");
  	}  	
  </script>
   <div id="shoppingCartReminder"></div>
  
  <style>
   #cartLink {margin-left:5px}
   #shoppingCartReminder {position:absolute;right:20%;}
  </style>

  <br><br>
  
  <script>
  		var minTextField = new sap.ui.commons.TextField("minTF",{
  			tooltip:"Minimum Price"
  		});
  		
  		var maxTextField = new sap.ui.commons.TextField("maxTF",{
  			tooltip:"Maximum Price"
  		});
  		
  		minTextField.setValue("${minPrice}");
  		maxTextField.setValue("${maxPrice}");  		
  		
  		var oText = new sap.ui.commons.Label({text:"-"});
  		
  		var oSubmitPriceFilterBtn = new sap.ui.commons.Button({text: "Submit",press:function(){
  			window.location.href = "bookServlet?method=getBooks&minPrice="+minTextField.getValue()+"&maxPrice="+maxTextField.getValue();
  		}});
  		
  		var oPriceFilterLayout = new sap.ui.layout.HorizontalLayout({
  			content: [minTextField, oText, maxTextField, oSubmitPriceFilterBtn]
  		});
  		
  		oPriceFilterLayout.placeAt("priceFilter");
  		
  </script>
  <style>
  		#minTF {margin-right: 5px;}
  		#maxTF {margin:0 5px}
  		
  </style>
  
  <div id="priceFilter" style="position: absolute; right:20%"></div>
  <br><br>
 
 
 
 
 <script>
 
 var aProducts = [];   
 

 <c:forEach items = "${bookpage.list }" var = "book">
   var oProduct = {};
   oProduct.id = "${book.id}";
   oProduct.category = "${book.author}";
   oProduct.price = "${book.price}";
   oProduct.name = "${book.name}";
   oProduct.minPrice="${minPrice}";
   oProduct.maxPrice= "${maxPrice}";
   oProduct.pageNo="${bookpage.pageNo}";
   oProduct.keyword= "${keyword}";
   
   aProducts.push(oProduct);
   
 </c:forEach>
 
 jQuery.sap.require("sap.ui.core.IconPool"); 
 sap.ui.core.Icon.extend("addToCart",{
	    metadata:{
	        properties:{
	          "itemId":"string",
	      	  "itemName":"string"
	        },
	        
	        events:{
	        	"click": {}
	        }
	    },
	    onclick: function(oEvent){
	          this.fireClick();
	    },
	    renderer: {}
	});
 
 sap.ui.commons.Link.extend("customLink",{
	metadata:{
		properties:{
			"itemId":"string"
		}
	},
	renderer:{}
 });
 var oProducts = JSON.stringify(aProducts);
 var oParameters;
 
 function createTemplate(){
	    var c = sap.ui.commons;
		 
	      var oItem =  new sapUiCustomItemLayout({
	    	  itemId:"{id}",
        	  pageNo:"${pageNo}",
        	  itemName:"{name}",
        	  minPrice:"${minPrice}", 
        	  maxPrice:"${maxPrice}",
        	  
        	  
        	link: new customLink({text: "{name}",itemId:"{id}",press:function(){
        		window.location.href="bookServlet?method=getBook&id="+this.getItemId()+"&minPrice=${minPrice}"+"&keyword=${keyword}&maxPrice=${maxPrice}&pageNo=${pageNo}";
        	}}),
	        
	        image: new c.Image({src:"images/Notebook.png"}),       
	        	        
	        form: new c.form.Form({
	        	
	            width: "100%",
	            layout: new c.form.GridLayout(),
	            formContainers: [
	                new c.form.FormContainer({
	                    formElements: [
	                        new c.form.FormElement({
	                            label: new c.Label({
	                                text: "Category",
	                                layoutData: new c.form.GridElementData({hCells: "5"})}),
	                                
	                            fields: [new c.TextField({value: "{category}", editable: false})]
	                        }),
	                        new c.form.FormElement({
	                            label: new c.Label({text: "Price", layoutData: new c.form.GridElementData({hCells: "5"})}),
	                            fields: [new c.TextField({value: "{price}", editable: false})]
	                        }),
	                        new c.form.FormElement({
	                            label: new c.Label({text: "Rating", layoutData: new c.form.GridElementData({hCells: "5"})}),
	                            fields: [new c.RatingIndicator({value: 5, editable: false})]
	                        }),
	                        
	                        new c.form.FormElement({
	                            label: new c.Label({layoutData: new c.form.GridElementData({hCells: "5"})}),
	                            fields: [
	                                      new addToCart({
	                                    	  itemId:"{id}",
	                                    	  itemName:"{name}",
	                                    	  src: sap.ui.core.IconPool.getIconURI("cart"),
	                                          size: "20px",
	                                          color: "orange",
	                                          hoverColor: "blue",
	                                          click:function(){
	                                        	        sap.ui.commons.MessageBox.confirm("You've added "+this.getItemName()+" to the cart!",fnCallbackConfirm,"Confirm");
	                                        	  	    var sHref= "bookServlet?method=addToCart&pageNo=${pageNo}&id="+this.getItemId()+"&keyword=${keyword}"+"&name="+this.getItemName()+"&minPrice=${minPrice}&maxPrice=${maxPrice}";
	                                        	        //var postData = {pageNo:"${pageNo}",id:this.getItemId(),keyword:"${keyword}",name:this.getItemName(),maxPrice:"${maxPrice}",minPrice:"${minPrice}"};
	                                        	  	    function addToCart(){
	                                        	  	    	window.location.href= sHref; 	                                        	  	    	
	                                        	        }
	                                        	        
	                                        	        function fnCallbackConfirm(result){
	                                        	  	    	if(result){
	                                        	  	    		     addToCart();   
	                                        	  	    	}
	                                        	  	    }           	  
	                                          }
	                                       })
	                                    ]
	                        })

	                    ]
	                        })
	                    ]
	                })
	            
	        });
	      
	      
	      
	    return oItem;
	}
 
 
 var oModel = new sap.ui.model.json.JSONModel();
 oModel.setData({data:aProducts});
 var oDataSet = new sap.ui.ux3.DataSet({
     items: {
         path: "/data",
         template: new sap.ui.ux3.DataSetItem({
             title : "{name}",
             iconSrc :"images/Notebook.png"
         })
     },
     views: [
         new sap.ui.ux3.DataSetSimpleView({
             name: "Floating, responsive View",
             icon: "images/tiles2_hover.png",
             floating: true,
             responsive: true,
             itemMinWidth: 300,
             template: createTemplate()
         }),

         new sap.ui.ux3.DataSetSimpleView({
             name: "Floating, non-responsive View",
             icon:"images/list_hover.png",
             floating: true,
             responsive: false,
             itemMinWidth: 0,
             template: createTemplate()
         })
     ],

     layoutData : new sap.ui.layout.GridData({
         span : "L12 M12 S12"
     }),

     search: function search(oEvent) {
         var sQuery = oEvent.getParameter("query");
         window.location.href="bookServlet?method=getBooks&minPrice="+"${minPrice}"+"&keyword="+sQuery+"&maxPrice="+"${maxPrice}";
     	
              
     },
     selectionChanged: function search(oEvent) {
         //var idx = oEvent.getParameter("newLeadSelectedIndex");
         //alert("Product '"+oDataSet.getItems()[idx].getTitle()+"' selected.'");
     }
 });


 oDataSet.setModel(oModel);
 
 oDataSet.placeAt("uiArea");
 
 sap.ui.getCore().byId("__set0-searchValue").setValue("${keyword}")
 </script>
 

        
        <div id="uiArea" style="width:60%;margin-left:auto;margin-right:auto"></div>
        <br><br>
        
        <script>
        
        var oPaginator = new sap.ui.commons.Paginator({
        	currentPage: ${bookpage.pageNo},
        	numberOfPages: ${bookpage.totalPageNumber},
        	page: function(oEvent){
        		var targetPageNo = oEvent.getParameters().targetPage;
        		window.location.href="bookServlet?method=getBooks&minPrice=${minPrice}&maxPrice=${maxPrice}"+"&pageNo="+targetPageNo+"&keyword=${keyword}";
        	}
        });  
        
        
        oPaginator.placeAt("pageNoLayout");
        </script>
        
        <div id="pageNoLayout" style="margin-left:40%;margin-right:40%"></div>

</body>
</html>