jQuery(function(){
	
	// Add new row of form fields dynamically for advanced search
	var rowCount = $("#advanced_table tr").length - 2; // Used to determine the ID of the current last text input field
	var selectRow = "input[name='advanced_search"+rowCount+"']"; // The last row that isn't submit
	var idRowCount = rowCount + 1; // Used for entering the name and id of the possible new last row
	$("#advanced_table select[name='searchBool"+rowCount+"']").hide();
	// Adds a listener to the last text input box
	function addRow() {
		$("#advanced_table").on("keypress", selectRow, function() {
			$("#advanced_table select[name='searchBool"+ rowCount +"']").show();
			$("#advanced_table tr:last").before('<tr> \
					<td> \
					<select name="searchParam'+idRowCount+'"> \
		            <option value="variablename">Variable Name</option> \
		            <option value="variablelabel">Label</option> \
		            <option value="variabletext">Full Description</option> \
		            <option value="variableconcept">Concept</option> \
		            <option value="variablecodeinstructions">Variable Type</option> \
					</select> \
					</td> \
					<td> \
					<input type="text" id="advanced_search'+idRowCount+'" name="advanced_search'+idRowCount+'" size="30" maxlength="30" /> \
					</td> \
					<td> \
					<select name="searchBool'+idRowCount+'"> \
					<option value="and">And</option> \
					<option value="or">Or</option> \
					<option value="and_not">And Not</option> \
					</select><br /> \
					</td> \
			</tr>');

			$("#advanced_table").off("keypress", selectRow); // Make it so the old input box won't add new rows
			// Update the variables with the new length
			rowCount = $("#advanced_table tr").length - 2;
			selectRow = "input[name='advanced_search"+rowCount+"']";
			idRowCount = rowCount + 1;
			$("#advanced_table select[name='searchBool"+rowCount+"']").hide();
			addRow(); // Add a listener for the new last row
		});
	}

	addRow(); // When the page first loads add a listener to last text input field for the advanced search 
	
	// Reset the value of search fields to nothing when page is loaded
	$("input[type='text']").val('');
});