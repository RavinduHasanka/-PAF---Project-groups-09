package beans;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

public class CardPayment extends  Payment implements Serializable {
    private  String cardNumber;
    private int expYear;
    private int expMonth;
    private int securityCode;
    private String cardHoldersName;

    public CardPayment() {}

    public CardPayment(double amount, Date paymentDate, boolean refunded, Date refundedDate, int cartId,double refundAmount,
			String cardNumber, int expYear, int expMonth, int securityCode, String cardHoldersName) {
		super(amount, paymentDate, refunded, refundedDate, cartId,refundAmount);
		this.cardNumber = cardNumber;
		this.expYear = expYear;
		this.expMonth = expMonth;
		this.securityCode = securityCode;
		this.cardHoldersName = cardHoldersName;
	}





	public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardHoldersName() {
        return cardHoldersName;
    }

    public void setCardHoldersName(String cardHoldersName) {
        this.cardHoldersName = cardHoldersName;
    }
    
}
