package beans;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

public class PaypalPayment extends  Payment implements Serializable{

    public String paypalReferenceNumber;

    public PaypalPayment() {}
    
	public PaypalPayment(double amount, Date paymentDate, boolean refunded, Date refundedDate, int cartId,double refundAmount,
			String paypalReferenceNumber) {
		super(amount, paymentDate, refunded, refundedDate, cartId,refundAmount);
		this.paypalReferenceNumber = paypalReferenceNumber;
	}

	public String getPaypalReferenceNumber() {
		return paypalReferenceNumber;
	}

	public void setPaypalReferenceNumber(String paypalReferenceNumber) {
		this.paypalReferenceNumber = paypalReferenceNumber;
	}
        
}
