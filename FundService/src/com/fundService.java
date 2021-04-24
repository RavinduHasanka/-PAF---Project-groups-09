package com;

import model.fund;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

@Path("/Fund")
public class fundService {
	fund fundObj = new fund();

	@GET
	@Path("/ReadAll")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return fundObj.readFunds();
	}
	
	@GET
	@Path("/ReadOnce/{ID}")
	@Produces(MediaType.TEXT_HTML)
	public String readItem(@PathParam("ID") String ID) 
	{ 
		return fundObj.readFund(ID); 
	}

	@GET
	@Path("/Test")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Welcome to Fund Service.";
	}
	
	@POST
	@Path("/Insert") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertFund(@FormParam("requesterName") String requesterName, 
							@FormParam("requesterPhone") String requesterPhone, 
							@FormParam("requesterMail") String requesterMail, 
							@FormParam("requesterDesc") String requesterDesc,
							@FormParam("requesterNIC") String requesterNIC) 
	{ 
		String output = "";
		if( requesterName.isEmpty() || requesterPhone.isEmpty() || requesterMail.isEmpty() || requesterDesc.isEmpty() || requesterNIC.isEmpty() ) {
			output = "Please Fill All Fields !";
		}
		else {
				output = fundObj.insertFund(requesterName, requesterPhone, requesterMail, requesterDesc,requesterNIC); 
		}
		return output; 
	}
	
	@DELETE
	@Path("/Delete") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteFund(String fundData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(fundData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String fundID = doc.select("fundID").text(); 
	 String output = fundObj.deleteFund(fundID); 
	return output; 
	}

	@PUT
	@Path("/Update") 
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
	 String output = "";
		if( requesterName.isEmpty() || requesterPhone.isEmpty() || requesterMail.isEmpty() || requesterDesc.isEmpty() || requesterNIC.isEmpty() ) {
			output = "Please Fill All Fields !";
		}
		else {
			output = fundObj.updateFund(fundID, requesterName, requesterPhone, requesterMail, requesterDesc, requesterNIC); 
		}
	return output; 
	}
}
