package OrderMgmt.model;

import java.math.BigDecimal;

/* class that handles the updated status and prices that will be set
by the operator */
public class RequestsUpdate {
	private Status updStatus;
	private BigDecimal updPrice;
	
	public RequestsUpdate() {}
	
	// getters and setters
	public Status getStatus() {return updStatus;}
	public void setStatus(Status s) {this.updStatus = s;}
	
	public BigDecimal getUpdatedPrice() {return updPrice;}
	public void setUpdatedPrice(BigDecimal up) {this.updPrice = up;}
	
	
}
