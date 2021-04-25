package com;

import model.Item;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Items")
public class ItemService {
	Item itemObj = new Item();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return itemObj.readItems();
	}

	
	@GET
	@Path("/Test")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Welcome to item Service.";
	}
	
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(
	 @FormParam("itemCode") String itemCode, 
	 @FormParam("itemName") String itemName, 
	 @FormParam("itemPrice") String itemPrice, 
	 @FormParam("itemDesc") String itemDesc,
	 @FormParam("num_of_items") String num_of_items) 
	{ 
	 String output = itemObj.insertItem(itemCode, itemName, itemPrice, itemDesc,num_of_items); 
	return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String itemID = itemObject.get("itemID").getAsString(); 
	 String itemCode = itemObject.get("itemCode").getAsString(); 
	 String itemName = itemObject.get("itemName").getAsString(); 
	 String itemPrice = itemObject.get("itemPrice").getAsString(); 
	 String itemDesc = itemObject.get("itemDesc").getAsString();
	 String num_of_items = itemObject.get("num_of_items").getAsString();
	 String output = itemObj.updateItem(itemID, itemCode, itemName, itemPrice, itemDesc,num_of_items); 
	return output; 
	}

	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String itemID = doc.select("itemID").text(); 
	 String output = itemObj.deleteItem(itemID); 
	return output; 
	}

}
