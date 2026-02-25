package OrderMgmt.service;

import java.util.List;

import OrderMgmt.model.*;

public interface CustomerService {
	// listing all products on sale
		List<Product> getAvailProducts();
		
		// placing the order method
		Order placeOrder(Order order);
		
		// getter for getting the customer's order information
		List<Order> getCustOrders(Integer custID);
		
		// Cancel orders methods
		Order cancelOrder(Integer orderID);
}
