package OrderMgmt.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// auto incerement
	private Integer ordID; 
	
	@Column(name = "cust_id", nullable = false)
	private Integer custID;
	
	@Column(name = "prod_code", nullable = false)
	private String prodCode;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status orderStatus;
	
	public Order() {}
	
	// constructor
	public Order(Integer custID, String prodCode, Integer quantity) {
		this.custID = custID;
		this.prodCode = prodCode;
		this.quantity = quantity;
		this.orderDate = LocalDateTime.now();
		this.orderStatus = Status.PENDING;	// default
	}
	
	// getters and setters
	public Integer getOrdID() {return ordID;}
	public void setOrdID(Integer id) {this.ordID = id;}
	
	public Integer getCustID() {return custID;}
	public void setCustID(Integer custID) {this.custID = custID;}
	
	public Integer getQuantity() {return quantity;}
	public void setQuantity(Integer quantity) {this.quantity = quantity;}
	
	public String getProdCode() {return prodCode;}
	public void setProdCode(String pc) {this.prodCode = pc;}
	
	public LocalDateTime getDate() {return orderDate;}
	public void setDate(LocalDateTime date) {this.orderDate = date;}
	
	public Status getStatus() {return orderStatus;}
	public void setStatus(Status s) {this.orderStatus = s;}
	
}
