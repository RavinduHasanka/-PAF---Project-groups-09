package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class fund {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return con;
	}

	public String insertItem(String requesterName, String requesterPhone, String requesterMail, String requesterDesc , String requesterNIC) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = "insert into fund (fundID,requesterName,requesterPhone,requesterMail,requesterDesc,requesterNIC) values (?, ?, ?, ?, ?, ?);"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, requesterName);
			preparedStmt.setString(3, requesterPhone);
			preparedStmt.setString(4, requesterMail);
			preparedStmt.setString(5, requesterDesc);
			preparedStmt.setString(6, requesterNIC);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = e.getMessage()+"  Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Requester Name</th><th>Requester Phone</th>" + "<th>Requester Mail</th>"
					+ "<th>Requester Description</th>" + "<th>Requester NIC</th>" +"<th>Update</th><th>Remove</th></tr>";

			String query = "select * from fund";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String fundID = Integer.toString(rs.getInt("fundID")); 
				 String requesterName = rs.getString("requesterName"); 
				 String requesterPhone = rs.getString("requesterPhone"); 
				 String requesterMail = rs.getString("requesterMail"); 
				 String requesterDesc = rs.getString("requesterDesc"); 
				 String requesterNIC = rs.getString("requesterNIC");
				 // Add into the html table
				 output += "<tr><td>" + requesterName + "</td>"; 
				 output += "<td>" + requesterPhone + "</td>"; 
				 output += "<td>" + requesterMail + "</td>"; 
				 output += "<td>" + requesterDesc + "</td>"; 
				 output += "<td>" + requesterNIC + "</td>";
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
				 		+ "class='btn btn-secondary'></td>"
				 		+ "<td><form method='post' action='items.jsp'>"
				 		+ "<input name='btnRemove' type='submit' value='Remove'"
				 		+ "class='btn btn-danger'>"
				 		+ "<input name='itemID' type='hidden' value='" + fundID
				 		+ "'>" + "</form></td></tr>"; 
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateItem(String fundID, String requesterName, String requesterPhone, String requesterMail, String requesterDesc ,String requesterNIC) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE fund SET requesterName=?,requesterPhone=?,requesterMail=?,requesterDesc=? ,requesterNIC=? "
					+ "WHERE fundID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, requesterName);
			preparedStmt.setString(2, requesterPhone);
			preparedStmt.setString(3, requesterMail);
			preparedStmt.setString(4, requesterDesc);
			preparedStmt.setString(5, requesterNIC);
			preparedStmt.setInt(6, Integer.parseInt(fundID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = e.getMessage();
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteItem(String fundID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from fund where fundID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
