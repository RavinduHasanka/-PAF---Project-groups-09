package services;

import beans.Payment;
import beans.CardPayment;
import beans.PaypalPayment;
import enums.PaymentMethod;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

	private Connection sqlConnection;

	public PaymentService(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

	//select payment method
	public boolean makePayment(Payment payment) throws SQLException {
		if (payment.getClass().getCanonicalName() == CardPayment.class.getCanonicalName()) {
			return makeCardPayment((CardPayment) payment);
		} else if (payment.getClass().getCanonicalName() == PaypalPayment.class.getCanonicalName()) {
			return makePaypalPayment((PaypalPayment) payment);
		} else {
			return false;
		}

	}
	
	//insert common payment details
	private void assignCommonParams(PreparedStatement statement, Payment payment) throws SQLException {
		statement.setInt(1, payment.getCartId());
		statement.setDouble(2, payment.getAmount());
		statement.setDate(3, new Date(new java.util.Date().getTime()));
	}
	
	//insert card payment details
	private boolean makeCardPayment(CardPayment payment) throws SQLException {
		String sql = "INSERT INTO "
				+ "`tbl_payments`(`appointment_id`, `amount`, `payment_date`, `card_number`, `exp_year`, `exp_month`, `security_code`, `card_holders_name`, `type`)"
				+ " VALUES (?,?,?,?,?,?,?,?,?);";
		PreparedStatement statement = this.sqlConnection.prepareStatement(sql);
		this.assignCommonParams(statement, payment);
		statement.setString(4, payment.getCardNumber());
		statement.setInt(5, payment.getExpYear());
		statement.setInt(6, payment.getExpMonth());
		statement.setInt(7, payment.getSecurityCode());
		statement.setString(8, payment.getCardHoldersName());
		statement.setString(9, PaymentMethod.CreditCard.name());

		return statement.execute();
	}



	//insert paypal payment details
	private boolean makePaypalPayment(PaypalPayment payment) throws SQLException {
		String sql = "INSERT INTO `tbl_payments`( `appointment_id`, `amount`, `payment_date`,  `paypal_payment_refarance`, `type`) "
				+ "VALUES (?,?,?,?,?)";
		PreparedStatement statement = this.sqlConnection.prepareStatement(sql);
		this.assignCommonParams(statement, payment);
		statement.setString(4, payment.getPaypalReferenceNumber());
		statement.setString(5, PaymentMethod.PayPal.name());

		return statement.execute();
	}
	
	//update payment details via making a refund
	public boolean makeRefund(Payment payment)throws SQLException  {
		String sql ="update  `tbl_payments` set refunded=? ,`refunded_date`=?, `refund_amount`=? where `appointment_id`=?";
		PreparedStatement statement=this.sqlConnection.prepareStatement(sql);
		statement.setBoolean(1, true);
		statement.setDate(2, new Date(new java.util.Date().getTime()));
		statement.setDouble(3, payment.getRefundAmount());
		statement.setInt(4, payment.getCartId());
		return statement.execute();
	}
	
	//delete refund details
	public boolean unRefund(Payment payment)throws SQLException  {
		String sql ="update  `tbl_payments` set refunded=? ,`refunded_date`=?, `refund_amount`=? where `appointment_id`=?";
		PreparedStatement statement=this.sqlConnection.prepareStatement(sql);
		statement.setBoolean(1, false);
		statement.setDate(2, null);
		statement.setDouble(3, 0);
		statement.setInt(4, payment.getCartId());
		return statement.execute();
	}

	//view payment details
	public List<Payment> getAllPayments() throws SQLException {
		String sql = "SELECT " + "`id`, " // 1
				+ "`cart_id`," // 2
				+ " `amount`, " // 3
				+ "`payment_date`, " // 4
				+ "`refunded`, " // 5
				+ "`refunded_date`, " // 6
				+ "`card_number`, "// 7
				+ "`exp_year`, "// 8
				+ "`exp_month`, "// 9/
				+ "`security_code`, "// 10
				+ "`card_holders_name`, "// 11
				+ "`online_payment_refarance`, "// 12
				+ "`paypal_payment_refarance`, "// 13
				+ "`type`, "// 14
				+"`refund_amount`" //15
				+ "FROM `tbl_payments`";
		ResultSet resultSet = this.sqlConnection.prepareStatement(sql).executeQuery();
		List<Payment> payments = new ArrayList<>();

		while (resultSet.next()) {
			String type = resultSet.getString(14);
			 
		 if(type.equals(PaymentMethod.CreditCard.name()))
				payments.add(new CardPayment(resultSet.getDouble(3), resultSet.getDate(4), resultSet.getBoolean(5),
						resultSet.getDate(6), resultSet.getInt(2),resultSet.getDouble(15), resultSet.getString(7), resultSet.getInt(8),
						resultSet.getInt(9), resultSet.getInt(10), resultSet.getString(11)));
		 else if(type.equals(PaymentMethod.PayPal.name()))
			 payments.add(new PaypalPayment(resultSet.getDouble(3), resultSet.getDate(4), resultSet.getBoolean(5),
						resultSet.getDate(6), resultSet.getInt(2),resultSet.getDouble(15), resultSet.getString(13)));
				 
		}
		
		return payments;
	}


	

}
