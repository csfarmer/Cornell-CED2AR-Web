$(document).ready(function() {

	var query = "";
	
	if (location.hash.length) {
		$("#simpleSearchDiv").hide();
		$("#results").html("<img src=\"../images/loading.gif\">");
		$("#results").show();
		query = location.hash.replace('#', '');
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
	}
	
	$("#simple_search").submit(function() {
		$("#simpleSearchDiv").hide();
		$("#results").html("<img src=\"../images/loading.gif\">");
		$("#results").show();
		var query = document.getElementsByName("query")[0].value;
		location.hash = query;
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