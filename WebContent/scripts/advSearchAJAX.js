$(document).ready(function() {
	var query = "";
	if (location.hash.length) {
		$("#advanced_search").hide();
		$("#results").html("Loading...");
		$("#results").show();
		query = location.hash.replace('#', '');
		$.ajax({
			type: "POST",
			url: "AdvancedSearchServlet",
			data: $('#advanced_search').serialize(),
			success: function(response) {
				$("#results").html(response);
				$("#simpleSearchBack").on("click", function() { 
					$("#results").hide();
					$("#advanced_search").show();
				});
			}
		});
	}
	
	$("#advanced_search").submit(function() {
		$("#advanced_search").hide();
		$("#results").html("Loading...");
		$("#results").show();
		location.hash = $('#advanced_search').serialize();
		$.ajax({
			type: "POST",
			url: "AdvancedSearchServlet",
			data: $('#advanced_search').serialize(),
			success: function(response) {
				$("#results").html(response);
			}
		}); 

		return false;
	}); 

});