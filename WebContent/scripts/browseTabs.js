jQuery(function(){

	// Modeled after http://matthewaprice.com/jquery-tabs-without-jquery-ui/
	// Create the tabbed navigation HTML list
	$('#browse-content').before('<ul id="browseTabs"><li id="tab-codebook" class="selected">Browse by Codebook</li> | <li id="tab-alphabet">Browse Alphabetically</li></ul>');
	$('#browse-content').addClass('js');
	// Whenever the tab is clicked, show the content for that tab and hide the content of the other tabs
	$("#browseTabs li").each(function() {

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
