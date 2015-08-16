sap.ui.core.Control.extend("counter",{
    metadata:{
        properties:{
            "size": {type:"sap.ui.core.CSSSize", defaultValue:"20px"},
            "value": {type:"string", defaultValue:"5"}

        },
        
        events:{
        	"change": {}
        }
    },

    renderer : function(oRm, oControl) { // static function, so use the given "oControl" instance 
                                           // instead of "this" in the renderer function
          oRm.write("<div"); 
          oRm.writeControlData(oControl);  // writes the Control ID and enables event handling - important!
          oRm.addClass("counter");        // add a CSS class for styles common to all Control instances
          oRm.writeClasses();           // this call writes the above class plus enables support 
          oRm.write(">");

          oRm.write("<div");
          oRm.addClass("minus");          
          oRm.writeClasses();
          oRm.addStyle("position","relative");
          oRm.addStyle("float","left");
          oRm.addStyle("cursor","pointer");
          oRm.writeStyles();
          oRm.write(">");
          oRm.write("-");
          oRm.write("</div>");

          oRm.write("<div");
          oRm.writeAttribute("contentEditable",true);
          oRm.addStyle("border","1px solid gray");
          oRm.addStyle("width",oControl.getSize());
          oRm.addStyle("height",oControl.getSize());
          oRm.addStyle("position","relative");
          oRm.addStyle("margin-left","5px");
          oRm.addStyle("margin-right","5px");
          oRm.addStyle("padding","1px");
          oRm.addStyle("float","left");
          oRm.writeStyles();
          oRm.addClass("numContainer");
          oRm.writeClasses();
          oRm.write(">");
          oRm.write(oControl.getValue());
          oRm.write("</div>");

          oRm.write("<div");
          oRm.addClass("plus");
          oRm.writeClasses();
          oRm.addStyle("cursor","pointer");
          oRm.addStyle("position","relative");
          oRm.addStyle("float","left");
          oRm.writeStyles();
          oRm.write(">");
          oRm.write("+");
          oRm.write("</div>");
          oRm.write("</div>");
      },

      onAfterRendering: function(oRm, oControl){
        var ctrl = this;
        var id = ctrl.getId();
        $("#"+id+" .minus").click(function(){
            if(ctrl.getValue()>0){
                var newVal = parseInt(ctrl.getValue()) - 1;
                ctrl.setValue(newVal);
                $("#"+id+" .numContainer").html(newVal);  
                ctrl.fireChange();
            }
        });

        $("#"+id+" .plus").click(function(){
            
                var newVal = parseInt(ctrl.getValue()) + 1;
                ctrl.setValue(newVal);
                $("#"+id+" .numContainer").html(newVal);
                ctrl.fireChange();
            
        });

        $("#"+id+" .numContainer").focusout(function(){
        	if($(this).html()!= ctrl.getValue().toString()){
        		 ctrl.setValue($(this).html());
        		ctrl.fireChange();
        	}
           
        })
        
        

      }
});
