$(document).ready(function(){

	
	// Make the dropdown list submit automatically on change
    $(".alphabetChar").click(function() { 
    	variableName = $(this).attr("id");
    		
    	var alphabetHeader = '<h2>Variables Starting with "' + variableName + '"</h2> \
   		<hr /> \
   		<table class="alphabetTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdMiddle\">Label</td><td class=\"tdRight\">Codebook</td></tr></table> \
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