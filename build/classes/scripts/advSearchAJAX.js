$(document).ready(function() {
	var query = "";
	if (location.hash.length) {
		$("#advanced_search").hide();
		$("#results").html("<img src=\"images/loading.gif\">");
		$("#results").show();
		query = location.hash.replace('#', '');
		query = query.replace(/!/g, '&');

		$.ajax({
			type: "POST",
			url: "AdvancedSearchServlet",
			data: query,
			success: function(response) {
				$("#results").html(response);
				
				$("#advancedSearchBack").on("click", function() { 
					$("#results").hide();
					$("#advanced_search").show();
				});
			}
		});
	}
	
	$("#advanced_search").submit(function() {
		$("#advanced_search").hide();
		$("#results").html("<img src=\"images/loading.gif\">");
		$("#results").show();
		location.hash = $('#advanced_search').serialize();
		$.ajax({
			type: "POST",
			url: "AdvancedSearchServlet",
			data: $('#advanced_search').serialize(),
			success: function(response) {
				$("#results").html(response);

				$("#advancedSearchBack").on("click", function() { 
					$("#results").hide();
					$("#advanced_search").show();
				});
			}
		}); 

		return false;
	}); 

});