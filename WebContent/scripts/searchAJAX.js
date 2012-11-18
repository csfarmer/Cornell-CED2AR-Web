$(document).ready(function() {

	$("#simple_search").submit(function() {
		$("#simpleSearchDiv").hide();
		$("#results").html("Loading...");
		$("#results").show();
		var query = document.getElementsByName("query")[0].value;
		$.ajax({
			type: "get",
			url: "SearchServlet",
			data: "query=" + query,
			success: function(response) {
				$("#results").html(response);
				
				$("#simpleSearchBack").on("click", function() { 
					$("#results").hide();
					$("#simpleSearchDiv").show();
				});
			}
		}); 

		return false;
	}); 

});