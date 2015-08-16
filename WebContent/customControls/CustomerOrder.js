
sap.ui.core.ListItem.extend("CustListItem",{
    metadata:{
        properties:{
            price: {type:"string"},
            num:{type:"string"},
            article:{type:"string"}
        }
    }
});


sap.ui.core.Control.extend("sap.ui.custom.CustomerOrderList",{
    metadata:{
        properties:{
            "oId": {type:"string"},
            "vendorName":{type:"string",defaultValue:"sap"},
            "vendorID":{type:"string",defaultValue:"0081131017"},
            "orderDate":{type:"string",defaultValue:"2015"},
            "sum": {type:"string",defaultValue:"1000"},
            "model":{type : "any", defaultValue: new sap.ui.model.json.JSONModel()},
            "path":{type:"string",defaultValue:"/"}
        },

        aggregations:{
            "items" : "CustListItem",
            "buttonCheck": "sap.ui.commons.Button"
            
        }
    },
    
    init: function(){
    	
    	/*var oCtrl = this;
    	var aData = oCtrl.getModel().getProperty(oCtrl.getPath());
		
    	
        	for(var i=0; i<aData.length;i++){
        		var template = new CustListItem({
        			price:aData[i].price,
        			num:aData[i].num,
        			article:aData[i].article,
        			additionalText:aData[i].additionalText
        		});
        		oCtrl.addAggregation("items",template);
        		
        	}*/
    /*	var Layout = new CustListItem();
    	var template = new CustListItem({
			price:"{price}",
			num:"{num}",
			article:"{article}",
			additionalText:"{additionalText}"
		});
    	
    	Layout.bindElement(this.getPath(),template);
    	
    	this.addAggregation("items",Layout);*/
    	
    },
    
    renderer: {
    	render: function(oRm, oControl){
    		
    	
        oRm.write("<div");
        oRm.writeControlData(oControl);
        oRm.addClass("customerOrder");
        oRm.writeClasses();
        oRm.addStyle("width","1000px");  
        oRm.addStyle("height", (oControl.getItems().length*100+50).toString()+"px"); 
        oRm.addStyle("margin","10px");      
        oRm.writeStyles();

        oRm.write(">");

        oRm.write("<div");
        oRm.addClass("orderTitle");
        oRm.writeClasses();
        oRm.addStyle("background-color","   #E0FFFF");
        oRm.addStyle("width","100%");   
        oRm.addStyle("height","25px");
        
        oRm.writeStyles();
        oRm.write(">");

        oRm.write("<div class='cb' style='float:left; margin-left:10px; width:5%'>");
        
        oRm.renderControl(new sap.ui.commons.CheckBox());  

        oRm.write("</div>");

        oRm.write("<div class='orderDate' style='float:left; margin-top:2px; width:10%'>");
        oRm.renderControl(new sap.ui.commons.Label({
            text: oControl.getOrderDate()+" "
        })) ;      
        oRm.write("</div>");

        oRm.write("<div class='oId'style='float:left ; margin-top:2px; width:30%'>");
        oRm.renderControl(new sap.ui.commons.Label({
            text: "Order ID: "+ oControl.getOId()+" "
        })) ;
        
        oRm.write("</div>");


        oRm.write("<div class='vendorName'style='float:left ; margin-top:2px; width:20%'>");
        oRm.renderControl(new sap.ui.commons.Label({
            text: "Vendor: "+ oControl.getVendorName()+" "
        })) ;

        oRm.write("</div>");

        oRm.write("<div class='vendorID'style='float:left; margin-top:2px;  width:30%'>");
       
        oRm.renderControl(new sap.ui.commons.Label({
            text: "("+ oControl.getVendorID()+") "
        })) ;
        oRm.write("</div>");

        oRm.write("</div>");

        oRm.write("<div");
        oRm.addClass("itemLists");
        oRm.writeClasses();
        oRm.addStyle("float","left");
        oRm.addStyle("width","700px");
        oRm.writeStyles();
        oRm.write(">");

        for(var i=0; i<oControl.getItems().length; i++){
	        oRm.write("<div");
	        oRm.addClass("item");
	        oRm.writeClasses();
	        oRm.addStyle("border-left","1px solid   #E0FFFF");
	        oRm.addStyle("border-bottom","1px solid     #E0FFFF");
	        oRm.addStyle("width","100%");
	        //oRm.addStyle("padding","20px");
	        oRm.addStyle("height","100px");
	        oRm.writeStyles();
	        oRm.write(">");
	
	        oRm.write("<div class='itemImage' style='width:60px;float:left'>");
	
	        var oImage = new sap.ui.commons.Image();        
	        oImage.setSrc("images/Notebook.png");
	        oImage.setHeight("60px");
	        oRm.renderControl(oImage);
	        oRm.write("</div>");
	
	        oRm.write("<div class='itemContainer'>");
	        
	        oRm.write("<div class='itemTitle'>");
	        var oInput = new sap.ui.commons.Label();
	        oInput.setText(oControl.getItems()[i].getArticle());
	        oInput.setWrapping(true);
	        oInput.setWidth("200px");
	        
	        oRm.renderControl(oInput);
	        oRm.write("</div>");
	
	        oRm.write("<div class='itemDescription'>");
	        var oInput2 = new sap.ui.commons.TextView();
	        oInput2.setText(oControl.getItems()[i].getAdditionalText());
	        oInput2.setWrapping(true);
	        oInput2.setWidth("200px");        
	
	        oRm.renderControl(oInput2);
	        
	        oRm.write("</div>");
	         oRm.write("</div>")
	
	
	        oRm.write("<div class='itemPrice'>");
	        var oInput3 = new sap.ui.commons.Label();
	        oInput3.setText(oControl.getItems()[i].getPrice());
	        oInput3.setWrapping(true);
	        oInput3.setWidth("50px");        
	
	        oRm.renderControl(oInput3);
	        
	        oRm.write("</div>");
	
	
	        oRm.write("<div class='itemNum'>");
	        var oInput3 = new sap.ui.commons.Label();
	        oInput3.setText(oControl.getItems()[i].getNum());
	        oInput3.setWrapping(true);
	        oInput3.setWidth("50px");        
	
	        oRm.renderControl(oInput3);
	        
	        oRm.write("</div>");
	
	        oRm.write("<div class='itemClaim'>");
	
	        oRm.write("<div class='retour'>");
	        var oInput3 = new sap.ui.commons.Label(
	            
	            
	        );
	        oInput3.setText("Retour/Change");
	        oInput3.setWrapping(true);
	        
	               
	
	        oRm.renderControl(oInput3);
	        oRm.write("</div>");
	
	
	        oRm.write("<div class='complaint'>");
	        var oInput4 = new sap.ui.commons.Label();
	        oInput4.setText("complaint");
	        oInput4.setWrapping(true);
	        oRm.renderControl(oInput4);
	        oRm.write("</div>");
	        
	        oRm.write("</div>");
	
	        oRm.write("</div>");
        }

        oRm.write("</div>");

        oRm.write("<div text-align='center'");
        oRm.addClass("status");
        oRm.writeClasses();
        oRm.addStyle("border-left","1px solid   #E0FFFF");
        oRm.addStyle("border-bottom","1px solid     #E0FFFF");
        oRm.addStyle("width","148px");
        oRm.addStyle("height",((oControl.getItems().length-1)*1 + oControl.getItems().length*100).toString()+"px");
        oRm.addStyle("float","left");
        oRm.writeStyles();       
        oRm.write(">");

        oRm.write("<div class='orderSum' align='center'>");
        var oInput5 = new sap.ui.commons.Label();
        oInput5.setText("total: "+ oControl.getSum());
        oInput5.setWrapping(true);
        oInput5.setTextAlign(sap.ui.core.TextAlign.Center);
        oRm.renderControl(oInput5);  
        oRm.write("</div>");


        oRm.write("<div class='logisticTrack' align='center'>");
        var oInput5 = new sap.ui.commons.Label();
        oInput5.setText("On Delivery");
        oInput5.setWrapping(true);
        oInput5.setTextAlign(sap.ui.core.TextAlign.Center);
        oRm.renderControl(oInput5);  
        oRm.write("</div>");

        oRm.write("</div>");

        oRm.write("<div");
        oRm.addClass("note");
        oRm.writeClasses();
        oRm.addStyle("border-right","1px solid  #E0FFFF");

        oRm.addStyle("border-left","1px solid   #E0FFFF");
        oRm.addStyle("border-bottom","1px solid #E0FFFF");
        oRm.addStyle("width","148px");
        oRm.addStyle("height",((oControl.getItems().length-1)*1 + oControl.getItems().length*100).toString()+"px");
        oRm.addStyle("float","left");   
        oRm.writeStyles();
        oRm.write(">");

        oRm.write("<div class='confirmReceive' align='center'>");
        oRm.renderControl(oControl.getButtonCheck()[0]); 
        oRm.write("</div>");


        oRm.write("</div>");

        oRm.write("</div>");
    	}
    }
});
