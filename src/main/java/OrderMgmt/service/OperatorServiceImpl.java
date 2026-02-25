package OrderMgmt.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import OrderMgmt.model.*;
import OrderMgmt.repository.*;

@Component
public class OperatorServiceImpl implements OperatorService{
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private OrderRepository ordRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private WholesalerService wholesalerServ;
	
	// View orders and customer details [AI Collaborated]
	public List<Map<String, Object>> viewAllOrders(){
	    List<Order> orders = ordRepo.findAll();
	    List<Map<String, Object>> result = new ArrayList<>();
	    
	    // Hardcoded customer data
	    Map<Integer, Map<String, String>> customers = Map.of(
	        1, Map.of("name", "Mackenzie Anderson", "email", "m.anderson@outlook.com", "address", "Isle of Skye"),
	        2, Map.of("name", "Ava Escobar", "email", "a.escobar@outlook.com", "address", "Stirling"),
	        3, Map.of("name", "Raylan Gallegos", "email", "r.gallegos@outlook.com", "address", "Edinburgh Castle")
	    );
	    
	    for(Order o: orders) {
	        HashMap<String, Object> orderHashMap = new HashMap<>();
	        
	        // Add order details
	        orderHashMap.put("orderID", o.getCustID());
	        orderHashMap.put("custID", o.getCustID());
	        orderHashMap.put("productCode", o.getProdCode());
	        orderHashMap.put("quantity", o.getQuantity());
	        orderHashMap.put("status", o.getStatus());
	        orderHashMap.put("orderDate", o.getDate());
	        
	        // Add hardcoded customer details
	        Map<String, String> customer = customers.get(o.getCustID());
	        if (customer != null) {
	            orderHashMap.put("custName", customer.get("name"));
	            orderHashMap.put("custEmail", customer.get("email"));
	            orderHashMap.put("custAddress", customer.get("address"));
	        } else {
	            orderHashMap.put("customerName", "Unknown Customer");
	            orderHashMap.put("customerEmail", "N/A");
	            orderHashMap.put("customerAddress", "N/A");
	        }
	        
	        result.add(orderHashMap);
	    }
	    return result;
	}
	// Get all products in the database
	public List<Product> getAllProducts(){
		return prodRepo.findAll();
	}
	
	// Get product stock from wholesaler service
	public Product getProdStock(String prodCode) {
		return wholesalerServ.getProductStock(prodCode);
	}
	
	// Changing order status
	public Order changeOrderStat(Integer orderID, Status stat) {
		Optional<Order> orderOpti = ordRepo.findById(orderID);
		
		if (orderOpti.isPresent()) {
			Order o = orderOpti.get();
			o.setStatus(stat);
			return ordRepo.save(o);
		}	
		return null;
	}
	
	// Update retail prices of products
	public Product updateProductRP(String prodCode, BigDecimal newRP) {
		Optional<Product> prodOpti = prodRepo.findById(prodCode);
		
		if (prodOpti.isEmpty()) {
			return null;
		}
		Product p = prodOpti.get();
		p.setRetailPrice(newRP);
		return prodRepo.save(p);
	}
	
	// View customer's total revenue
	public BigDecimal getCustRevenue(Integer custID) {
		BigDecimal revenue = ordRepo.calcCustRevenue(custID);
	    return revenue != null ? revenue : BigDecimal.ZERO;
	}
	
	// add and remove product
	public Product addProduct(Product prod) {
	    if (prod == null || prod.getProdCode() == null) {
	        return null;
	    }
	    if (prod.getOnSaleStatus() == null) {
	        prod.setOnSaleStatus(true);  // This is correct!
	    }
	    return prodRepo.save(prod);
	}
	public void removeProduct(String prodCode) {
		if(prodCode != null) {
			prodRepo.deleteById(prodCode);
		}
	}
	
	// add and remove product from sale
	public Product addToSale(String prodCode) {
		Optional<Product> prodOpt = prodRepo.findById(prodCode);
		
		if(prodOpt.isPresent()) {
			Product p = prodOpt.get();
			p.setOnSaleStatus(true); 		// setting the product on sale = true
			return prodRepo.save(p);
		}
		return null;	// if it's empty
	}
	
	public Product removeFromSale(String prodCode) {
		Optional<Product> prodOpt = prodRepo.findById(prodCode);
		if(prodOpt.isPresent()) {
			Product p = prodOpt.get();
			p.setOnSaleStatus(false); // setting the product NOT on sale = false
			return prodRepo.save(p);
		}
		return null;	// if it's empty
	}
}
