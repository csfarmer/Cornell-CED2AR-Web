var query = "";
$(document).ready(function() {	
	if (location.hash.length) {		
		query = location.hash.replace('#', '');
		queryRepository(0);
	} 

	$("#simple_search").submit(function() {	
		query = document.getElementsByName("query")[0].value;
		location.hash = query;
		queryRepository(0);
		return false;
	}); 
	
});

function queryRepository(currpage){	
	$("#simpleSearchDiv").hide();
	$("#results").html("<img src=\"images/loading.gif\">");
	$("#results").show();

	$.ajax({
		type: "get",
		url: "SearchServlet",
		data: "query=" + query + "&page=" + currpage,
		success: function(response) {
			$("#results").html(response);
			
			$("#simpleSearchBack").on("click", function() { 
				$("#results").hide();
				$("#simpleSearchDiv").show();
			});
		}
	}); 
}