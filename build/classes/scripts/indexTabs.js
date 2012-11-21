jQuery(function(){

	// Modeled after http://matthewaprice.com/jquery-tabs-without-jquery-ui/
	// Create the tabbed navigation HTML list
	$('#tabs-content').before('<ul id="tabs"><li id="tab-simple" class="selected">Simple Search</li><li id="tab-advanced" class="middle">Advanced Search</li><li id="tab-browse">Browse Data</li></ul>');
	$('#tabs-content').addClass('js');
	// Whenever the tab is clicked, show the content for that tab and hide the content of the other tabs
	$("#tabs li").each(function() {

		$(this).click(function() {
			var tempId = $(this).attr('id');
			var tabId = tempId.split('-');
			var tabContent = document.getElementById('tab-content-' + tabId[1]);
			tabContent.style.display = 'block';
			$(this).addClass('selected');
			$(this).siblings().removeClass('selected');
			$(tabContent).siblings().css('display', 'none');
		});
	});

});