var codebookName = "";
$(document).ready(function(){
	
	if (location.hash.length && location.hash != "#default") {
		codebookName = location.hash.replace('#', '');			
		getCodebook('0');
	}
	
	// Make the dropdown list submit automatically on change
    $("#chooseCodebookSelect").change(function() { 
    	if ($(this).val() != "default") {
    		codebookName = $(this).val();
        	location.hash = codebookName;
    		$("#chooseCodebook").submit();
    	}
    });
    
    // When a codebook is selected change the content displayed on the page
    $("#chooseCodebook").submit( function() {
		getCodebook('0');
		return false;
    });

});

function getCodebook(currpage){
	var codebookHeader = '<h2>Browsing Codebook: ' + codebookName + '</h2> \
	 <hr /> \
	 <table class="codebookTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdRight\">Label</td></tr></table> \
	 <hr />';
	
	$("#browseCodebookHeader").html(codebookHeader);
	
	$("#browseAlphabetContent").html("<img src=\"images/loading.gif\">");    
	// AJAX Call to ViewBrowseCodebook and retrieve HTML Data
	$.ajax({
		type: "get",
		url: "ViewBrowseCodebook",
		data: "codebook=" + codebookName + "&page=" + currpage,
		success: function(response) {
			$("#browseCodebookContent").html(response);
		}
	});
}