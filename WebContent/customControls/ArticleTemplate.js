jQuery.sap.declare("sap.ui.custom.ArticleTemplate");
sap.ui.core.Control.extend("sap.ui.custom.ArticleTemplate",{
	metadata:{
		properties:{
			imgURL: {type:"sap.ui.core.URI"},
			articleTags: {type:"string[]"}
		}
		
	},
	aggregations:{
		_rating: {type:"sap.ui.commons.RatingIndicator", multiple:"false", visibility:"hidden"},
		
	},

	init: function(){
		var oCtrl = this; 
		oCtrl.articleImage = new sap.ui.commons.Image({src:"images/schuhe.jpg",width:"280px"});
		var oRater = new sap.ui.commons.RatingIndicator("myRating", {
			maxValue: 5,
			editable:true,
			visualMode: sap.ui.commons.RatingIndicatorVisualMode.Continuous
		});
		oRater.setValue(4.6);
		oRater.setVisible(false);
		oCtrl.setAggregation("_rating",oRater);
		
		
	
	
	},
	
	renderer: function(oRm, ctrl){
		oRm.write("<div");
		oRm.writeControlData(ctrl);
		oRm.writeClasses;
		oRm.addStyle("width","300px");
		oRm.addStyle("border","solid 1px gray");
		oRm.addStyle("padding","10px")
		oRm.writeStyles();
		oRm.write(">");
		
				
		oRm.write("<div");		
		oRm.addStyle("width","50%");
		oRm.addStyle("margin-left","25%");
		oRm.addStyle("margin-right","auto");
		oRm.writeStyles();
		oRm.write(">");
		oRm.renderControl(ctrl.getAggregation("_rating"));
		oRm.write("</div>");
		
		for(var i=0; i<ctrl.getArticleTags().length;i++){
			oRm.write("<div>");
			oRm.renderControl(new sap.ui.commons.Label({
				text:ctrl.getArticleTags()[i],
				design: sap.ui.commons.LabelDesign.Bold
			}));
			oRm.write("</div>");
		}
		
		
		oRm.write("<div");		
		oRm.writeStyles();
		oRm.write(">");
		oRm.renderControl(ctrl.articleImage);
		oRm.write("</div>");
		
		oRm.write("djfifkdf");
		
		oRm.write("</div>");
	},
	
	onmouseover: function(){
		this.getAggregation("_rating").setVisible(true);
		
	},
	
	onmouseout: function(){
		this.getAggregation("_rating").setVisible(false);
	}
});