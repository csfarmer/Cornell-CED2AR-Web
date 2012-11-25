$(document).ready(function(){

	var variableName = "";
	// Detect a hash in the URL and make an AJAX call. This is for when the user hits the back button in a browser
	if (location.hash.length) {
		variableName = location.hash.replace('#', '');
		
	   	var alphabetHeader = '<h2>Variables Starting with "' + variableName + '"</h2> \
   		<hr /> \
   		<table class="alphabetTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdMiddle\">Label</td><td class=\"tdRight\">Codebook</td></tr></table> \
   		<hr />';

   		$("#browseAlphabetHeader").html(alphabetHeader);
      $("#"+variableName).addClass("selected-browse");
   		
		$("#browseAlphabetContent").html("<img src=\"images/loading.gif\">");
    
		// AJAX Call to ViewBrowseCodebook and retrieve HTML Data
		$.ajax({
			type: "get",
			url: "ViewBrowseAlphabet",
			data: "variableName=" + variableName,
			success: function(response) {
				$("#browseAlphabetContent").html(response);
			}
		});
	}
	
	// Make the dropdown list submit automatically on change
    $(".alphabetChar").click(function() { 
    	var variableName = $(this).attr("id");
      $("#content").find(".alphabetChar.selected-browse").removeClass("selected-browse");
      $(this).addClass("selected-browse");
    	location.hash = variableName;
    	var alphabetHeader = '<h2>Variables Starting with "' + variableName + '"</h2> \
   		<hr /> \
   		<table class="alphabetTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdMiddle\">Label</td><td class=\"tdRight\">Codebook</td></tr></table> \
   		<hr />';

   		$("#browseAlphabetHeader").html(alphabetHeader);
   		
		$("#browseAlphabetContent").html("<img src=\"images/loading.gif\">");
    
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