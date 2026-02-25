package OrderMgmt.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;

@Entity
@Table(name = "products")

public class Product {
	@Id
	@Column(name = "prod_code")
	private String prodCode; // from wholesaler, connected to Order's prodCode
	
	@Column(name = "description", nullable = false)
	private String desc; 	// product description
	
	@Column(name = "retail_price", nullable = false, precision = 10, scale = 2)		// TOTAL 10 Digits, after 2 decimal places
	private BigDecimal retailPrice;
	
	// column that shows True or False if the product is on sale or not
	@Column(name = "on_sale", nullable = false)
	private Boolean onSale = true;		// default value
	
	@Transient		// will not be saved on the local database, only based from external API
	@JsonInclude(JsonInclude.Include.NON_NULL)  // Only include if not null
	private Integer inStock;
	
	// constructor
	public Product() {}
	
	public Product(String prodCode, String desc, BigDecimal retailPrice) {
		this.prodCode = prodCode;
		this.desc = desc;
		this.retailPrice = retailPrice;
		this.onSale = true;		// default is it's always on sale
	}
	
	// getters and setters
	public String getProdCode() {return prodCode;}
	public void setProdCode(String pc) {this.prodCode = pc;}
	
	public String getDescription() {return desc;}
	public void setDescription(String desc) {this.desc = desc;}
	
	public BigDecimal getRetailPrice() {return retailPrice;}
	public void setRetailPrice(BigDecimal rp) {this.retailPrice = rp;}
	
	public Boolean getOnSaleStatus() {return onSale;}
	public void setOnSaleStatus(Boolean onSale) {this.onSale = onSale;}
	
	public Integer getInStock() {return inStock;}
	public void setInStock(Integer inStock) {this.inStock = inStock;}
	
}
