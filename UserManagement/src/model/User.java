package model;

import java.sql.*;

public class User {
	
	
	//----------------------------------------Connect to the DB-------------------------------------------
	
	private Connection connect() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gadget_badget", "root", "root");
		}
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return con;
	}
	
	//-----------------------------------------Insert User Details Method---------------------------------------------
	
	public String insertUserDetails(String fname, String lname, String nic, String address, String phone, String email, String username, String password, String type) {
		
		String result = "";
		
		//Validation part
		String emailValid = "^(.+)@(.+)$";
		String nicValid1 = "^[0-9]{9}[vV]$";
		String nicValid2  = "^[0-9]{12}$";
		String phoneValid = "^[0-9]{10}$";
		
		if(fname.isEmpty() || lname.isEmpty() || nic.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
			result = "Cannot Insert Empty Fields! ";
		}
		
		else if(!(nic.matches(nicValid1) || nic.matches(nicValid2))) {
			result = "Invalid NIC Number!";
		}
		
		else if(!(phone.matches(phoneValid))) {
			result = "Invalid Phone Number!";
		}
		
		else if(!(email.matches(emailValid))) {
			result = "Invalid Email Address!";
		}
			
		
		else if(password.length() < 6) {
			result = "Password is not Strong!";
		}
		
		else {
		
			try {
				Connection con = connect();
				
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				
				//Insert query 
				String query = "insert into user (`userID`, `fname`, `lname`, `nic`, `address`, `phone`, `email`, `username`, `password`, `type`)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
				
				PreparedStatement preparedStat = con.prepareStatement(query);
				
				preparedStat.setInt(1, 0);
				preparedStat.setString(2, fname);
				preparedStat.setString(3, lname);
				preparedStat.setString(4, nic);
				preparedStat.setString(5, address);
				preparedStat.setString(6, phone);
				preparedStat.setString(7, email);
				preparedStat.setString(8, username);
				preparedStat.setString(9, password);
				preparedStat.setString(10, "Buyer");
							
			
				preparedStat.execute();
				con.close();
				
				result = "User Registation Successfully.";
				
					
			
			}
			catch(Exception e) 
			{
				result = "Error while inserting the user.";
				System.err.println(e.getMessage());
			}
		}
		
		
		
		return result;
		
	}
	
	//-------------------------------------------------Read User Details Method--------------------------------------------
	
	public String readUserDetails() {
		
		String result = "";
		
		try {
			Connection con = connect();
			
			if (con == null) 
			{
				return "Error while connecting to the database for reading.";
			}
			
			result = "<table border = '1'> <tr><th>First Name</th> "
					+ "<th>Last Name</th>"
					+ "<th>NIC</th> "
					+ "<th>Address</th> "
					+ "<th>Phone</th> "
					+ "<th>Email</th> "
					+ "<th>Username</th>"
					+ "<th>Password</th> </tr> ";
					//+ "<th>Type</th> "
					//+ "<th>Update</th> <th>Remove</th> </tr>";
			
			String query = "select * from user";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(query);
			
			while (rs.next()) 
			{
				String user = Integer.toString(rs.getInt("userID"));
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				//String type = rs.getString("type");
				
				result += "<tr><td>" + fname + "</td>";
				result += "<td>" + lname + "</td>";
				result += "<td>" + nic + "</td>";
				result += "<td>" + address + "</td>";
				result += "<td>" + phone + "</td>";
				result += "<td>" + email + "</td>";
				result += "<td>" + username + "</td>";
				result += "<td>" + password + "</td>";
				//result += "<td>" + type + "</td>";
							
				
				/*result += "<td> <input type = 'button' name = 'btn update' value = 'Update' class = 'btn btn-secondary'> </td>" 
						+ "<td> <form method = 'post' action = 'items.jsp'> "
						+ "<input type = 'submit' name = 'btnRemove' value = 'Remove' class = 'btn btn-danger'>"
						+ "<input type = 'hidden' name = 'userID' value = '" + userID + "'> "
						+ "</form></td></tr>"; */
				
			}
			
			con.close();
			
			result += "</table>";
			
		}
		
		catch(Exception e) 
		{
			result = "Error while reading the user.";
			System.err.println(e.getMessage());
		}
		
		return result;
		
	}
	
	//---------------------------------------------Read details of relevant user type------------------------------------
	
	public String readUserType(String type) {
		
		String result = "";
		
		try {
			Connection con = connect();
			
			if (con == null) 
			{
				return "Error while connecting to the database for reading.";
			}
		
			result = "<table border = '1'> <tr><th>First Name</th> "
					+ "<th>Last Name</th>"
					+ "<th>NIC</th> "
					+ "<th>Address</th> "
					+ "<th>Phone</th> "
					+ "<th>Email</th> "
					+ "<th>Username</th>"
					+ "<th>Password</th> </tr> ";
					//+ "<th>Type</th> "
					//+ "<th>Update</th> <th>Remove</th> </tr>";
			
			String query = "select * from user where type=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, type);
			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) 
			{
				String user = Integer.toString(rs.getInt("userID"));
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				//String type = rs.getString("type");
				
				result += "<tr><td>" + fname + "</td>";
				result += "<td>" + lname + "</td>";
				result += "<td>" + nic + "</td>";
				result += "<td>" + address + "</td>";
				result += "<td>" + phone + "</td>";
				result += "<td>" + email + "</td>";
				result += "<td>" + username + "</td>";
				result += "<td>" + password + "</td>";
				//result += "<td>" + type + "</td>";
							
				
				/*result += "<td> <input type = 'button' name = 'btn update' value = 'Update' class = 'btn btn-secondary'> </td>" 
						+ "<td> <form method = 'post' action = 'items.jsp'> "
						+ "<input type = 'submit' name = 'btnRemove' value = 'Remove' class = 'btn btn-danger'>"
						+ "<input type = 'hidden' name = 'userID' value = '" + userID + "'> "
						+ "</form></td></tr>"; */
				
			}
			
			con.close();
			
			result += "</table>";
			
		}
		
		catch(Exception e) 
		{
			result = "Error while reading the user.";
			System.err.println(e.getMessage());
		}
		
		return result;
	}
	
	
	//-----------------------------------------Update User Details Method--------------------------------------------------
	
	public String updateUserDetails(String userID, String fname, String lname, String nic, String address, String phone, String email, String username, String password) {
		
		String result = "";
		
		//Validation part
		String emailValid = "^(.+)@(.+)$";
		String nicValid1 = "^[0-9]{9}[vV]$";
		String nicValid2  = "^[0-9]{12}$";
		String phoneValid = "^[0-9]{10}$";
		
		if(fname.isEmpty() || lname.isEmpty() || nic.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
			result = "Cannot Insert Empty Fields! ";
		}
		
		else if(!(nic.matches(nicValid1) || nic.matches(nicValid2))) {
			result = "Invalid NIC Number!";
		}
		
		else if(!(phone.matches(phoneValid))) {
			result = "Invalid Phone Number!";
		}
		
		else if(!(email.matches(emailValid))) {
			result = "Invalid Email Address!";
		}
			
		
		else if(password.length() < 6) {
			result = "Password is not Strong!";
		}
		
		else {
		
			try {
				Connection con = connect();
				
				if (con == null) 
				{
					return "Error while connecting to the database for updating.";
				}
				
				//Update query
				String query = "update user set fname=?, lname=?, nic=?, address=?, phone=?, email=?, username=?, password=? where userID=?";
				
				PreparedStatement preparedStat = con.prepareStatement(query);
				
				preparedStat.setString(1, fname);
				preparedStat.setString(2, lname);
				preparedStat.setString(3, nic);
				preparedStat.setString(4, address);
				preparedStat.setString(5, phone);
				preparedStat.setString(6, email);
				preparedStat.setString(7, username);
				preparedStat.setString(8, password);
				//preparedStat.setString(9, type);
				preparedStat.setInt(9, Integer.parseInt(userID));
				
				preparedStat.execute();
				con.close();
				
				result = "User Update Successfully";
				
			}
			
			catch(Exception e) 
			{
				result = "Error while updating the user.";
				System.err.println(e.getMessage());
			}
		}
		
		return result;
	}
	
	//----------------------------------------------Delete User Details Method----------------------------------------------
	
	public String deleteUserDetails (String userID) {
		
		String result = "";
		
		if(userID.isEmpty()) {
			result = "Please Insert UserID!";
		}
		
		else {		
		
			try {
				Connection con = connect();
				
				if (con == null) 
				{
					return "Error while connecting to the database for deleting.";
				} 
				
				//Delete query
				String query = "delete from user where userID=?";
				
				PreparedStatement preparedStat = con.prepareStatement(query);
				
				preparedStat.setInt(1, Integer.parseInt(userID));
				
				preparedStat.execute();
				con.close();
				
				result = "User Deleted Successfully";
				
			}
			
			catch(Exception e) 
			{	
				result = "Error while deleting the user.";
				System.err.println(e.getMessage());
			}
		}
		
		return result;
	}
	
	//--------------------------------------------------View User Details Method------------------------------------
	
	public String viewUserPfofile(String userID) {
			
			String result = "";		
			
			try {
				Connection con = connect();
				
				if (con == null) 
				{
					return "Error while connecting to the database for deleting.";
				} 
				
				result = "<table border = '1'> <tr><th>First Name</th> "
						+ "<th>Last Name</th>"
						+ "<th>NIC</th> "
						+ "<th>Address</th> "
						+ "<th>Phone</th> "
						+ "<th>Email</th> "
						+ "<th>Username</th>"
						+ "<th>Password</th></tr> ";
						//+ "<th>Type</th> "
						//+ "<th>Update</th> <th>Remove</th> </tr>";
				
				String query = "select * from user where userID='" + userID + "'";				
				
				Statement State = con.createStatement();
				
				ResultSet rs = State.executeQuery(query);
				
				while (rs.next()) 
				{
					String user = Integer.toString(rs.getInt("userID"));
					String fname = rs.getString("fname");
					String lname = rs.getString("lname");
					String nic = rs.getString("nic");
					String address = rs.getString("address");
					String phone = rs.getString("phone");
					String email = rs.getString("email");
					String username = rs.getString("username");
					String password = rs.getString("password");
					//String type = rs.getString("type");
					
					result += "<tr><td>" + fname + "</td>";
					result += "<td>" + lname + "</td>";
					result += "<td>" + nic + "</td>";
					result += "<td>" + address + "</td>";
					result += "<td>" + phone + "</td>";
					result += "<td>" + email + "</td>";
					result += "<td>" + username + "</td>";
					result += "<td>" + password + "</td>";
					//result += "<td>" + type + "</td>";
								
					
					/*result += "<td> <input type = 'button' name = 'btn update' value = 'Update' class = 'btn btn-secondary'> </td>" 
							+ "<td> <form method = 'post' action = 'items.jsp'> "
							+ "<input type = 'submit' name = 'btnRemove' value = 'Remove' class = 'btn btn-danger'>"
							+ "<input type = 'hidden' name = 'userID' value = '" + userID + "'> "
							+ "</form></td></tr>"; */
					
				}
				
				con.close();
				
				result += "</table>";
				
			}
			
			catch(Exception e) 
			{
				result = "Error while reading the user.";
				System.err.println(e.getMessage());
			}
			
			return result;
			
	}
	
	//-------------------------------------------Registered User Login Method-------------------------------------------
	
	public String login(String username, String password) {

		try {
			Connection con = connect();
			
			if (con == null) 
			{
				return "Error while connecting to the database for Loging.";
			}  

			System.out.println("wor");
			String query = "select `username`,`password` from `user` where `username` = ? and `password` = ?";
			
			PreparedStatement preparedStat = con.prepareStatement(query);			
			
			System.out.println(preparedStat);
			System.out.println(username);
			System.out.println(password);
			
			preparedStat.setString(1, username);
			preparedStat.setString(2, password);
			
			ResultSet rs = preparedStat.executeQuery();


			if(rs.next()) {
				
				con.close();
				return "Welcome "+ username +". "+"You are Successfully logged in.";
			}
			
			else {
				con.close();
				
				if(username.equals("")) {
					return "Username cannot be empty";
				}
				
				else if(password.equals("")) {
					return "Password cannot be empty";
				}
				else {
					return  "Incorrect Username or Password ! ";
				}
			}

		}
		catch(Exception e) 
		{
			
			System.out.println(e);
			return "Error while connecting to the database for login.";

		}

	}
		
}

