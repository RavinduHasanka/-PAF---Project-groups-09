package beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
 
 
 
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonResponse implements Serializable{
	private boolean status;
	private String messsage;
	
	private Object data;
	
	public CommonResponse() {}

	public CommonResponse(boolean status, String message) {
		this.status = status;
		this.messsage = message;
	}
	
	public CommonResponse(Object data) {
		this.status=true;
		this.data=data;
	}

	public static CommonResponse Error(Exception e) {
		return new CommonResponse(false, e.getMessage());
	}

	public static CommonResponse OK(String message) {
		return new CommonResponse(true, message);
	}
	public static CommonResponse OK(Object data) {
		return new CommonResponse(data);
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
