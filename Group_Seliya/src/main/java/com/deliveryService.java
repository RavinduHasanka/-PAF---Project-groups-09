package com;

import model.delivery;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
//import com.google.gson.*;
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Items")
public class deliveryService {
	delivery deliveryOb = new delivery();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return deliveryOb.readItems();
	}

	@GET
	@Path("/Test")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello world SEliya.";
	}
	
	@POST
	@Path("/Insert") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("ItemID") String ItemID, 
							@FormParam("ReceiverName") String ReceiverName, 
							@FormParam("ReceiverPhoneNo") String ReceiverPhoneNo, 
							@FormParam("ReceiverMail") String ReceiverMail) 
	{ 
		String output = "";
		if(ItemID.isEmpty()||ReceiverName.isEmpty()||ReceiverPhoneNo.isEmpty()||ReceiverMail.isEmpty() ) {
			output ="Fields Are Empty !";
		}
		else {
		output = deliveryOb.insertDelivery(  ItemID ,  ReceiverName,  ReceiverPhoneNo,  ReceiverMail); 
		}
		return output; 
	}
	
	@DELETE
	@Path("/Delete") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String DeliveryID = doc.select("DeliveryID").text(); 
	 String output = deliveryOb.deleteItem(DeliveryID); 
	return output; 
	}

	@PUT
	@Path("/Update") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject fundObject = new JsonParser().parse(itemData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String DeliveryID = fundObject.get("DeliveryID").getAsString(); 
	 String ItemID = fundObject.get("ItemID").getAsString(); 
	 String ReceiverName = fundObject.get("ReceiverName").getAsString(); 
	 String ReceiverPhoneNo = fundObject.get("ReceiverPhoneNo").getAsString(); 
	 String ReceiverMail = fundObject.get("ReceiverMail").getAsString(); 
	 String output = "";
		if(ItemID.isEmpty()||ReceiverName.isEmpty()||ReceiverPhoneNo.isEmpty()||ReceiverMail.isEmpty() ) {
			output ="Fields Are Empty !";
		}
		else {
		output = deliveryOb.insertDelivery(  ItemID ,  ReceiverName,  ReceiverPhoneNo,  ReceiverMail); 
		}
		return output; 
	}
	


}
