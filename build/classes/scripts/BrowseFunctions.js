$(document).ready(function(){
	var codebookName = "";
	
	// Choose codebook to browse by dropdown list
    $(".chooseCodebookSelect").change(function() { 
    	if ($(this).val() != "default") {
    		codebookName = $(this).val();
    		// Changes the text information in the div that contains the codebook variables/labels
    		var codebookHeader = '<h2>Browsing Codebook: ' + codebookName + '</h2> \
			 <hr /> \
			 <table class="codebookTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdRight\">Label</td></tr></table> \
			 <hr />';

    		$("#browseCodebookHeader").html(codebookHeader);
    		$("#browseCodebookContent").html("Loading...");
    		// AJAX Call to BrowseServlet and retrieve HTML Data
    		$.ajax({
    			type: "get",
    			url: "BrowseServlet",
    			data: "codebook=" + codebookName,
    			success: function(response) {
    				$("#browseCodebookContent").html(response);
    			}
    		});
    		
    	    
    	}
    	
    	
    });
    
    // When a variable is clicked load the information for that variable
    $(".variableName").live('click', function() {
    	var clickedVariable = $(this).attr("id");
    	$("#browseFormArea").html("<a href=\"\" class=\"backLink\"><< Back to List</a>");
    	// Update the codebookHeader area
    	var codebookHeader = '<hr /> \
    						  <h2>' + clickedVariable + '</h2> \
    						  <hr />';

		$("#browseCodebookHeader").html(codebookHeader);
   		$.ajax({
			type: "get",
			url: "BrowseServlet",
			data: "variableName=" + clickedVariable + "&codebook=" + codebookName,
			success: function(response) {
				$("#browseCodebookContent").html(response);
			}
		}); 
    	return false;
    });
    
    // When viewing variable info and the user selects back, go to the codebook screen
    $(".backLink").live('click', function() {
    	$("#browseFormArea").html("");
   		var codebookHeader = '<h2>Browsing Codebook: ' + codebookName + '</h2> \
		 <hr /> \
		 <table class="codebookTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdRight\">Label</td></tr></table> \
		 <hr />';

		$("#browseCodebookHeader").html(codebookHeader);
		$("#browseCodebookContent").html("Loading...");
		// AJAX Call to BrowseServlet and retrieve HTML Data
		$.ajax({
			type: "get",
			url: "BrowseServlet",
			data: "codebook=" + codebookName,
			success: function(response) {
				$("#browseCodebookContent").html(response);
			}
		});
		return false;
    });
    
    
});