package com;

import model.Payment;


//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Payment") 
public class PaymentService 
{
	
	Payment paymentObj = new Payment();
	

	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readPayment() 
	{ 
		return paymentObj.readpayment(); 
	} 	

	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertPayment(
		 @FormParam("paymentCode") String paymentCode, 
		 @FormParam("paymentType") String paymentType, 
		 @FormParam("customerName") String customerName,
		 @FormParam("customerContact") String customerContact,
		 @FormParam("totalAmount") String totalAmount,
		 @FormParam("cardNo") String cardNo)
		 
		 
	{ 
		String output = paymentObj.insertpayment(paymentCode, paymentType, customerName, customerContact,  totalAmount, cardNo); 
		return output; 
	}	
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updatePayment(String paymentData) 
	{ 
		//Convert the input string to a JSON object 
		 JsonObject orderObject = new JsonParser().parse(paymentData).getAsJsonObject(); 
		//Read the values from the JSON object
		 String paymentID = orderObject.get("paymentID").getAsString(); 
		 String paymentCode = orderObject.get("paymentCode").getAsString();
		 String paymentType = orderObject.get("paymentType").getAsString();   
		 String customerName = orderObject.get("customerName").getAsString();
		 String customerContact = orderObject.get("customerContact").getAsString();
		 String totalAmount = orderObject.get("totalAmount").getAsString(); 
		 String cardNo = orderObject.get("cardNo").getAsString();  
		
		
		 
		 String output = paymentObj.updatepayment(paymentID, paymentCode, paymentType,customerName, customerContact, totalAmount, cardNo); 
		 return output; 
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deletePayment(String paymentData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <paymentID>
		 String paymentID = doc.select("paymentID").text(); 
		 String output = paymentObj.deletepayment(paymentID); 
		 return output; 
	}
}
