var query = "";
$(document).ready(function() {
	if (location.hash.length) {
		query = location.hash.replace('#', '');
		query = query.replace(/!/g, '&');
		
		queryRepository('0');
	}
	
	$("#advanced_search").submit(function() {
		location.hash = $('#advanced_search').serialize();
		query = $('#advanced_search').serialize();
		queryRepository('0');
		return false;
	}); 

});

function queryRepository(currpage){
	$("#advanced_search").hide();
	$("#results").html("<img src=\"images/loading.gif\">");
	$("#results").show();
	
	$.ajax({
		type: "POST",
		url: "AdvancedSearchServlet",
		data: query + "&page=" + currpage,
		success: function(response) {
			$("#results").html(response);

			$("#advancedSearchBack").on("click", function() { 
				$("#results").hide();
				$("#advanced_search").show();
			});
		}
	}); 
}