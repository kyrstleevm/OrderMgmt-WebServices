package OrderMgmt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import OrderMgmt.model.*;
import OrderMgmt.repository.*;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private OrderRepository ordRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	// listing on sale products
	public List<Product> getAvailProducts(){
		return prodRepo.findByOnSaleTrue();
	}
	
	// Order a product method
	public Order placeOrder(Order order) {
		// check if the product requirements are null or not
		if (order == null || order.getCustID() == null || order.getProdCode() == null) {
			return null;
		}
		// setting default values to some order informations
		if (order.getStatus() == null) {
			order.setStatus(Status.PENDING);
		}
		if (order.getDate() == null) {
			order.setDate(java.time.LocalDateTime.now());
		}
		return ordRepo.save(order);
	}
	
	// Viewing past orders implementation
	public List<Order> getCustOrders(Integer custID){
		if(custID != null) {
			return ordRepo.findByCustID(custID);
		}
		return List.of();
	}
	
	// Cancelling orders implementation
	public Order cancelOrder(Integer orderID) {
		Optional<Order> orderOpti = ordRepo.findById(orderID);
		
		if (orderOpti.isPresent()) {
			Order o = orderOpti.get();
			if(o.getStatus() != Status.SHIPPED) {
				o.setStatus(Status.CANCELLED);
				return ordRepo.save(o);
			}
		}
		return null;	
	}
}
