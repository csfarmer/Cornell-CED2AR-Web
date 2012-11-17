$(document).ready(function(){
	var codebookName = "";
	
	// Make the dropdown list submit automatically on change
    $(".alphabetChar").click(function() { 
    	variableName = $(this).attr("id");
    		
    	var alphabetHeader = '<h2>Variables Starting with "' + variableName + '"</h2> \
   		<hr /> \
   		<table class="codebookTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdRight\">Label</td></tr></table> \
   		<hr />';

   		$("#browseAlphabetHeader").html(alphabetHeader);
   		
		//TODO: Adding loading GIF?
		$("#browseAlphabetContent").html("Loading...");
		// AJAX Call to ViewBrowseCodebook and retrieve HTML Data
		$.ajax({
			type: "get",
			url: "ViewBrowseAlphabet",
			data: "variableName=" + variableName,
			success: function(response) {
				$("#browseAlphabetContent").html(response);
			}
		});
    });
 
});