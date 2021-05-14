//hide alert
$(document).ready(function() {

	$("#alertSuccess").hide();
	$("#alertError").hide();
	$("#hidOrderIDSave").val("");
	$("#ORDER")[0].reset();
});

$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var type = ($("#paymentID").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentAPI",
		type : type,
		data : $("#ORDER").serialize(),
		dataType : "text",
		complete : function(response, status) {
			//console.log(status);
			onItemSaveComplete(response.responseText, status);
			window.location.reload(true);
		}
	});

});

function onItemSaveComplete(response, status) {
	
	if (status == "success") {
		
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#OrderGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error") {
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error") {
		
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
		
	} else {
		
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#paymentID").val("");
	$("#ORDER")[0].reset();
}

$(document).on("click", ".btnRemove", function(event) {
	
	$.ajax({
		url : "PaymentAPI",
		type : "DELETE",
		data : "paymentID=" + event.target.value,
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
			window.location.reload(true);
		}
	});
});

function onItemDeleteComplete(response, status) {
	
	if (status == "success") {
		
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#OrderGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error") {
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error") {
		
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
		
	} else {
		
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// UPDATE==========================================
$(document).on("click",".btnUpdate",function(event)
		{
			$("#paymentID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#paymentCode").val($(this).closest("tr").find('td:eq(1)').text());
			$("#paymentType").val($(this).closest("tr").find('td:eq(2)').text());
			$("#customerName").val($(this).closest("tr").find('td:eq(3)').text());
			$("#customerContact").val($(this).closest("tr").find('td:eq(4)').text());
			$("#totalAmount").val($(this).closest("tr").find('td:eq(5)').text());
			$("#cardNo").val($(this).closest("tr").find('td:eq(6)').text());
	
		});


// CLIENTMODEL=========================================================================
function validateItemForm() {
	
	// Payment Code
	if ($("#paymentCode").val().trim() == "") {
		return "Please insert payment code.";
	}
	
	// Payment Type
	if ($("#paymentType").val().trim() == "") {
		return "Please insert payment Type.";
	}
	
	// Customer Name
	if ($("#customerName").val().trim() == "") {
		return "Please insert Customer Name.";
	}
	
	// Customer Contact
	if ($("#customerContact").val().trim() == "") {
		return "Please insert Customer Contact Number.";
	}
	
	
	// Total Amount
	if ($("#totalAmount").val().trim() == "") {
		return "Please insert total amount.";
	}
	
	// Card No
	if ($("#cardNo").val().trim() == "") {
		return "Please insert card no.";
	}
	

	
	return true;
}
