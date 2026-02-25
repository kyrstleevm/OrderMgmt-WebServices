package OrderMgmt.model;

import jakarta.persistence.*;

@Entity
@Table (name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// auto increment
	private Integer custID;
	
	@Column(nullable = false)
	private String name, address;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	public Customer() 
	{
		
	}
	
	// getters and setters
	public Integer getCustID() { return custID; }
	public void setCustID(Integer id) { this.custID = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	public String getAdd() {return address;}
	public void setAdd(String address) {this.address = address;}
	
}
