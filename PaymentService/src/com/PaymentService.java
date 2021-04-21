package com;

import model.Payment;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payment")	
public class PaymentService {
		Payment paymentObj = new Payment();
		
		//Read Payment Details
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		
		public String readPaymentDetails() 
		{
			return paymentObj.readPaymentDetails();
		}
		
		//Insert Payment Details
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		
		public String insertPaymentDetails(
				
  		 
		 @FormParam("amount") String amount,
		 @FormParam("payment_date") String payment_date,
		 @FormParam("card_number") String card_number,
		 @FormParam("expire_mon_yer") String expire_mon_yer,
		 @FormParam("cvc") String cvc,
		 @FormParam("card_holder_name") String card_holder_name,
		 @FormParam("type") String type)
		
		{
		 
			String output = paymentObj.insertPaymentDetails( amount, payment_date,card_number ,expire_mon_yer, cvc, card_holder_name,type);
		 
			return output;
		}
		
		//Update Payment Details
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updatePaymentDetails(String paymentData)
		{
			
		//Convert the input string to a JSON object
		 JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		 
		//Read the values from the JSON object
		 String paymentID = paymentObject.get("paymenyID").getAsString();
		 String amount = paymentObject.get("amount").getAsString();
		 String payment_date = paymentObject.get("payment_date").getAsString();
		 String card_number = paymentObject.get("card_number").getAsString();
		 String expire_mon_yer = paymentObject.get("expire_mon_yer").getAsString();
		 String cvc = paymentObject.get("cvc").getAsString();
		 String card_holder_name = paymentObject.get("card_holder_name").getAsString();
		 String type = paymentObject.get("type").getAsString();
		 
		 
		 String output = paymentObj.updatePaymentDetails(paymentID, amount, payment_date, card_number ,expire_mon_yer, cvc, card_holder_name,type);
		 
		return output;
		}
		
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deletePaymenrDetails(String paymentData)
		{
			
		 //Convert the input string to an XML document
		 Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		 //Read the value from the element <paymentID>
		 String paymentID = doc.select("paymentID").text();
		 String output = paymentObj.deletePaymentDetails(paymentID);
		 
		 return output;
		}
}

