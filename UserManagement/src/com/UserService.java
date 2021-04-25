package com;

import model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/User")
public class UserService {
	
	
	
	User userObj = new User();
	
	//Read User Details
	@GET
	@Path("/View/")
	@Produces(MediaType.TEXT_HTML)
	
	public String readUserDetails() 
	{
		return userObj.readUserDetails();
	}
	
	//View User Profile Details
	@GET
	@Path("/Profile/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String readProfileDetails(@PathParam("userID") String userID) {
	
		return userObj.viewUserPfofile(userID);	
	}
	
	//find details of a certain user type(for admin)
	@GET
	@Path("/{type}")
	@Produces(MediaType.TEXT_PLAIN)
	
	public String readUserType(@PathParam("type") String type) {
		return userObj.readUserType(type) ; 
	}
	
	//Insert User Details
	@POST
	@Path("/Create/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String insertUserDetails(
		
		 @FormParam("fname") String fname,
		 @FormParam("lname") String lname,
		 @FormParam("nic") String nic,
		 @FormParam("address") String address,
		 @FormParam("phone") String phone,
		 @FormParam("email") String email,
		 @FormParam("username") String username,
		 @FormParam("password") String password,
		 @FormParam("type") String type)
	
	{
	 
		String output = userObj.insertUserDetails(fname, lname, nic, address, phone, email, username, password, type);	 
		return output;
	}
	
	//Update User Details
	@PUT
	@Path("/Update/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateUserDetails(String userData)
	{
		
		 //Convert the input string to a JSON object
		 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
		 
		 //Read the values from the JSON object
		 String userID = userObject.get("userID").getAsString();
		 String fname = userObject.get("fname").getAsString();
		 String lname = userObject.get("lname").getAsString();
		 String nic = userObject.get("nic").getAsString();
		 String address = userObject.get("address").getAsString();
		 String phone = userObject.get("phone").getAsString();
		 String email = userObject.get("email").getAsString();
		 String username = userObject.get("username").getAsString();
		 String password = userObject.get("password").getAsString();
		 //String type = userObject.get("type").getAsString();
		 
		 String output = userObj.updateUserDetails(userID, fname, lname, nic, address, phone, email, username, password);
		  
		 return output;
	}
	
	//Delete User Details
	@DELETE
	@Path("/Delete/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String deleteUserDetails(String userData)
	{
		
		 //Convert the input string to an XML document
		 Document doc = Jsoup.parse(userData, "", Parser.xmlParser());
	
		 //Read the value from the element <userID>
		 String userID = doc.select("userID").text();
		 String output = userObj.deleteUserDetails(userID);
		 
		 return output;
	}
	
	//Login User
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)	
	
	public String getlogin(String loging) {	
		
		Document doc = Jsoup.parse(loging, "", Parser.xmlParser());
		String username = doc.select("username").text();
		String password = doc.select("password").text();		
		
		String output = userObj.login(username,password);
		
		return output;
	
			
	}


}
