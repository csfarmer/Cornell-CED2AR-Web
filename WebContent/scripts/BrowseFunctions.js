$(document).ready(function(){
	// Choose codebook to browse by dropdown list
    $(".chooseCodebook").change(function() { 
    	if ($(this).val() != "default") {
    		this.form.submit();
    	}
    });
});