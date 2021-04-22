package com;

import model.fund;
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
public class fundService {
	fund fundObj = new fund();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return fundObj.readItems();
	}

	@GET
	@Path("/Test")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Welcome to Fund Service.";
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("requesterName") String requesterName, 
							@FormParam("requesterPhone") String requesterPhone, 
							@FormParam("requesterMail") String requesterMail, 
							@FormParam("requesterDesc") String requesterDesc,
							@FormParam("requesterNIC") String requesterNIC) 
	{ 
		String output = fundObj.insertItem(requesterName, requesterPhone, requesterMail, requesterDesc,requesterNIC); 
		return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String fundData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(fundData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String fundID = doc.select("fundID").text(); 
	 String output = fundObj.deleteItem(fundID); 
	return output; 
	}

	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String fundData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String fundID = fundObject.get("fundID").getAsString(); 
	 String requesterName = fundObject.get("requesterName").getAsString(); 
	 String requesterPhone = fundObject.get("requesterPhone").getAsString(); 
	 String requesterMail = fundObject.get("requesterMail").getAsString(); 
	 String requesterDesc = fundObject.get("requesterDesc").getAsString(); 
	 String requesterNIC = fundObject.get("requesterNIC").getAsString();
	 String output = fundObj.updateItem(fundID, requesterName, requesterPhone, requesterMail, requesterDesc, requesterNIC); 
	return output; 
	}


}
