package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class delivery {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadget_badget", "root", "");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return con;
	}

	public String insertDelivery(String ItemID, String ReceiverName, String ReceiverPhoneNo, String ReceiverMail) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = "insert into delivery (DeliveryID,ItemID,ReceiverName,ReceiverPhoneNo,ReceiverMail) values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, Integer.parseInt(ItemID));
			preparedStmt.setString(3, ReceiverName);
			preparedStmt.setString(4, ReceiverPhoneNo);
			preparedStmt.setString(5, ReceiverMail);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
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
			output = "<table border='1'><tr><th>Item ID</th><th>Receiver Name</th>" + "<th>Receiver Phone No</th>"
					+ "<th>Receiver Mail</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from delivery";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String DeliveryID = Integer.toString(rs.getInt("DeliveryID"));
				String ItemID = Integer.toString(rs.getInt("ItemID"));
				String ReceiverName = rs.getString("ReceiverName");
				String ReceiverPhoneNo = rs.getString("ReceiverPhoneNo");
				String ReceiverMail = rs.getString("ReceiverMail");
				// Add into the html table
				output += "<tr><td>" + ItemID + "</td>";
				output += "<td>" + ReceiverName + "</td>";
				output += "<td>" + ReceiverPhoneNo + "</td>";
				output += "<td>" + ReceiverMail + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btn btn-secondary'></td>" + "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'" + "class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + DeliveryID + "'>" + "</form></td></tr>";
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

	public String updateItem(String DeliveryID, String ItemID, String ReceiverName, String ReceiverPhoneNo,
			String ReceiverMail) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// mail validate
			String regexMail = "^(.+)@(.+)$";
			Pattern PatternMail = Pattern.compile(regexMail);
			Matcher matcherMail = PatternMail.matcher(ReceiverMail);

			// Phone validate
			String regexPhone = "^[0][0-9]{9}$ ";
			Pattern PatternPhone = Pattern.compile(regexPhone);
			Matcher matcherPhone = PatternPhone.matcher(ReceiverPhoneNo);

			if (matcherMail.matches() == true) {
				if (matcherPhone.matches() == true) {

					// create a prepared statement
					String query = "UPDATE delivery SET ItemID=?,ReceiverName=?,ReceiverPhoneNo=?,ReceiverMail=? "
							+ "WHERE DeliveryID=?";
					PreparedStatement preparedStmt = con.prepareStatement(query);
					// binding values
					preparedStmt.setInt(1, Integer.parseInt(ItemID));
					preparedStmt.setString(2, ReceiverName);
					preparedStmt.setString(3, ReceiverPhoneNo);
					preparedStmt.setString(4, ReceiverMail);
					preparedStmt.setInt(5, Integer.parseInt(DeliveryID));
					// execute the statement
					preparedStmt.execute();
					con.close();
					output = "Updated successfully";
				} else {
					output = "invalid Phone No";
				}
			} else {
				output = "invalid E-Mail";
			}
		} catch (Exception e) {
			output = e.getMessage() + " While Updating";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteItem(String DeliveryID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from delivery where DeliveryID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(DeliveryID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = e.getMessage() + "  Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
