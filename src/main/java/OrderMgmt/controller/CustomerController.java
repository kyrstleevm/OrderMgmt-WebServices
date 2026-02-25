package OrderMgmt.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.*;
import java.util.List;

import OrderMgmt.model.*;
import OrderMgmt.service.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
	private CustomerService cs;
	
	public CustomerController(CustomerService cs) {
		this.cs = cs;
	}
	
	// Request for getting available products
	@GetMapping("/products")
	public List<Product> getAvailProducts(){
		return cs.getAvailProducts();
	}
	
	// Creation of a new order
	@PostMapping("/orders")
	public ResponseEntity<Order> placeOrder(@RequestBody Order o){
		Order newOrder = cs.placeOrder(o);
		if (newOrder == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/customer/orders/" + 
		newOrder.getOrdID()).body(newOrder);
	}
	
	// Viewing all orders
	@GetMapping("/{custID}/orders")
	public List<Order> getCustOrders(@PathVariable Integer custID){
		List<Order> orders = cs.getCustOrders(custID);
		if (orders.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found.");
		}
		return orders;
	}
	
	// Cancelling the order
	@PutMapping("/orders/{orderID}/cancel")
	public ResponseEntity<Void> cancelOrder(@PathVariable Integer orderID){
		Order cancelOrder = cs.cancelOrder(orderID);
		if (cancelOrder == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter order ID");
		}
		// when curl -v, 204 - NO CONTENT will get returned 
		return ResponseEntity.noContent().build();
	}

}
