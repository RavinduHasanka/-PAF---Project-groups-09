package controllers;


import java.sql.SQLException; 
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import beans.Payment;
import beans.PaymentCartBean;
import beans.CardPayment;
import beans.CommonResponse;
import beans.PaypalPayment;
import model.PaymentCart;
import services.PaymentService;
import util.DBConnection;

@Path("payment")
public class PaymentController {
	private PaymentService paymentService;

	public PaymentController() throws ClassNotFoundException, SQLException {
		this.paymentService = new PaymentService(DBConnection.connect());
	}



	//Insert card payments details
	@RolesAllowed("admin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("card")
	public CommonResponse makePayment(CardPayment payment) {
		try {
			this.paymentService.makePayment(payment);
			return CommonResponse.OK("Success");
		} catch (Exception e) {
			return CommonResponse.Error(e);
		}
	}

	//Insert paypal payments details
	@RolesAllowed("admin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("paypal")
	public CommonResponse makePayment(PaypalPayment payment) {
		try {
			this.paymentService.makePayment(payment);
			return CommonResponse.OK("Success");
		} catch (Exception e) {
			return CommonResponse.Error(e);
		}
	}
	
	//View payments details
	@RolesAllowed("admin")
	@GET
	@Path("view")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Payment> view() throws SQLException {
		return this.paymentService.getAllPayments();
	}
	
	//Update payment details.
	//Make a refund details
	@RolesAllowed("admin")
	@PUT
	@Path("refund")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CommonResponse makeRefund(Payment payment) {
		try {
			this.paymentService.makeRefund(payment);
			return CommonResponse.OK("Success");
		} catch (Exception e) {
			return CommonResponse.Error(e);
		}
	}
	
	//Delete refund details
	@RolesAllowed("admin")
	@POST
	@Path("un-refund")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CommonResponse unRefund(Payment payment) {
		try {
			this.paymentService.unRefund(payment);
			return CommonResponse.OK("Success");
		} catch (Exception e) {
			return CommonResponse.Error(e);
		}
	}



	//Insert payment details from cart
	@RolesAllowed("admin")
	@POST
	@Path("insertPaymentFromAppointment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayAppointment(String cartPaydetails) {
		//payappbean get the appointPaydetails and get the values into the payment table related variables in it		
		PaymentCartBean  payAppbean =  new PaymentCartBean(cartPaydetails);
		payAppbean.ConvertStringToJSON();
		//this class insert the details to the database
		PaymentCart payapp =  new PaymentCart();		
		return payapp.InsertPayment(payAppbean);			
	}
	
	//Update payment details from cart
	@RolesAllowed("admin")
	@PUT
	@Path("updatePaymentFromAppointment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayAppointment(String cartPaydetails) {
		//payappbean get the cartPaydetails and get the values into the payment table related variables in it		
		PaymentCartBean  payAppbean =  new PaymentCartBean(cartPaydetails);
		payAppbean.ConvertStringToJSON();
		//this class update the details to the database
		PaymentCart payapp =  new PaymentCart();		
		return payapp.UpdatePayment(payAppbean);			
	}
	
	//Delete payment details from cart
	@RolesAllowed("admin")
	@DELETE
	@Path("deletePaymentFromCart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayCart(String cartPaydetails) {
		//payappbean get the cartPaydetails and get the values into the payment table related variables in it		
		PaymentCartBean  payAppbean =  new PaymentCartBean(cartPaydetails);
		payAppbean.ConvertStringToJSONdelete();
		//this class delete the details to the database
		PaymentCart payapp =  new PaymentCart();		
		return payapp.DeletePayment(payAppbean);			
	}
	

	
}
