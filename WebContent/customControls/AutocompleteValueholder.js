/**
 * Building a customized autocomplete valueholder demo
 */

sap.ui.core.Control.extend("AutocompleteValueholder",{
	metadata:{
		properties:{
			"codePropertyName":{type:"string", defaultValue:"code"},
			"descriptionPropertyName": {type : "string", defaultValue: "description"}, //Define a model property representing an item description  
		     "path": {type : "string", defaultValue: "/"}, //Define our model binding path  
		     "model": {type : "any", defaultValue: new sap.ui.model.json.JSONModel()} //Define our model  
			
		},
		aggregations:{
			"_layout" : {type : "sap.ui.layout.HorizontalLayout", multiple : false, visibility: "hidden"}
		},
		events:{
			"selectedItem":{},
			"deletedItem":{},
			"deletedAllItems":{}
			
		}
	},
	
	init: function(){
		var oControl = this;
		var searchField = new sap.ui.commons.AutoComplete(this.getId()+"-searchField",{
			maxPopupItems:5,
			displaySecondaryValues: true,
			change: function(event){
				var searchFieldControl = this;
				if(event.getParameters().selectedItem!=null){// if a list item is selected, add item to _layout aggregation
					var newValueField = new sap.ui.commons.TextField({
						width:"120px",
						editable:false						
					});
					
					newValueField.setModel(event.getSource().getModel());
					newValueField.bindProperty("value",oControl.getDescriptionPropertyName());
					newValueField.bindElement(event.getParameters().selectedItem.oBindingContexts.undefined.sPath);
					newValueField.addStyleClass("autoCompleteValueHolder_valueField");
					
					var newValueImage = new sap.ui.commons.Image({
						src: 'data:image/png;base64,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',    
						width:"18px",
						press: function(event){
							var valueLayout = event.getSource().getParent();
							var autoCompleteValueHolderLayout = oControl.mAggregations._layout;
							autoCompleteValueHolderLayout.removeContent(valueLayout);
							oControl.fireDeletedItem({allItems: oControl.getSelectedValues()})
						}
					});
					
					newValueImage.addStyleClass("autoCompleteValueHolder_valueImage");
					var valueLayout = new sap.ui.layout.HorizontalLayout({content:[newValueField,newValueImage]});
					valueLayout.addStyleClass("autoCompleteValueHolder_valueLayout");
					
					//add valuelayout to the position previous to the search textfield for better ux
					var content = oControl.mAggregations._layout.getContent();
					oControl.mAggregations._layout.insertContent(valueLayout,content.length-1);
					
					
					//fire the control selectedItem method when item is selected
					oControl.fireSelectedItem({
						newItem:{
							code:event.getParameters().selectedItem.getAdditionalText(),
							description:event.getParameters().selectedItem.getText()
						},
						allItems:oControl.getSelectedValues()
					});
					
					searchFieldControl.setValue("");
					
									
				}
			}
		});
		
		
		searchField.addStyleClass("autoCompleteValueHolder_search");
		var layout = new sap.ui.layout.HorizontalLayout(this.getId()+"-valuesLayout",{allowWrapping:true});
		layout.addContent(searchField);
		layout.addStyleClass("autoCompleteValueHolder_valuesLayout");
		this.setAggregation("_layout",layout);
		
	},
	
	renderer: {
		render: function(oRm, oControl){
			var layout = oControl.getAggregation("_layout");
			layout.getContent()[0].setModel(oControl.getModel());
			
			var template = new sap.ui.core.ListItem({
				text:"{"+oControl.getDescriptionPropertyName()+"}",
				additionalText:"{"+oControl.getCodePropertyName()+"}",
				
			});
			
			layout.getContent()[0].bindItems(oControl.getPath(),template);
			oRm.write("<span");
			oRm.writeControlData(oControl);
			oRm.writeClasses();
			oRm.write(">");
			
			oRm.renderControl(layout);
			oRm.write("</span>");
		}
	},
	
	onAfterRendering:function(){
		//this.mAggregations._layout.getContent()[0].focus();
	//	$("body").trigger("click");
	//	$("#__valueholder0-searchField-input").trigger("focus");
	},
	getSelectedValues: function() {  
		  var content = this.getAggregation("_layout").getContent();  
		  var result = [];  
		  if (content != null && content.length > 1) {  
		    //Get all selected item into result  
		    for (var i=0; i<content.length-1; i++) {  
		      var model = content[i].getContent()[0].getModel();  
		      var path = content[i].getContent()[0].getBindingContext().sPath;  
		      result.push(model.getProperty(path));  
		    }  
		  }  
		  return result;  
		}
	
});