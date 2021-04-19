package beans;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PaymentCartBean {	
	private int CartID ;
	private String cartPaydetails;
	private String Paymethod;
	private double Amount;
	
	public PaymentCartBean(String cartPaydetails) {
		this.cartPaydetails =  cartPaydetails;
	}

	public void ConvertStringToJSON() {
		JsonObject  AppointmentObject = new JsonParser().parse(cartPaydetails).getAsJsonObject(); 
		
		//get values into variables in PaymentAppointmentBean 
		setCartID(AppointmentObject.get("AppointmentID").getAsInt());
		setPaymethod(AppointmentObject.get("PaymentType").getAsString());
		setAmount(AppointmentObject.get("Amount").getAsDouble());
	}
	
	public void ConvertStringToJSONdelete() {
		JsonObject  AppointmentObject = new JsonParser().parse(cartPaydetails).getAsJsonObject(); 		
		//get values into variables in PaymentAppointmentBean 
		setCartID(AppointmentObject.get("CartID").getAsInt());		
	}
	
	public int getCartID() {
		return CartID;
	}

	public void setCartID(int cartID) {
		CartID = cartID;
	}

	public String getPaymethod() {
		return Paymethod;
	}

	public void setPaymethod(String paymethod) {
		Paymethod = paymethod;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

}
