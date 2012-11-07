$(document).ready(function(){
	// Choose codebook to browse by dropdown list
    $(".chooseCodebookSelect").change(function() { 
    	if ($(this).val() != "default") {
    		var codebookName = $(this).val();
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
    
    
});