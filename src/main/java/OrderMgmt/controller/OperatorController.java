package OrderMgmt.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.*;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import OrderMgmt.model.*;
import OrderMgmt.service.*;

@RestController
@RequestMapping("/operator")
@CrossOrigin
public class OperatorController {
	private OperatorService os;
	private WholesalerService ws;
	
	private final String OPERATOR_ACCESS = "Stir@123";
	
	public OperatorController(OperatorService os, WholesalerService ws) {
		this.os = os;
		this.ws = ws;
	}
	
	// Method that checks if the header matches the access code for the operator side
	private boolean authentication(String aHeader) {
		return aHeader != null && aHeader.equals(OPERATOR_ACCESS);
	}
	
	// Request of listing all orders
	@GetMapping("/orders")
	public List<Map<String, Object>> viewAllOrders(@RequestHeader("Authorization") String code){
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return os.viewAllOrders();
	}
	
	// Request of all PRODUCTS in the system
	@GetMapping("/products")
	public List<Product> getAllProducts(@RequestHeader("Authorization") String code){
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return os.getAllProducts();
	}
	
	// Request of getting stock of the product
	@GetMapping("/products/{prodCode}/stock")
		public Product getProdStock(@PathVariable String prodCode, @RequestHeader("Authorization") String code) {
			if(!authentication(code)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
			}
			Product stock = ws.getProductStock(prodCode);
			if(stock == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found");
			}
			return stock;
		}
	
	// Changing order status
	@PutMapping("/orders/{orderID}/status")
	public ResponseEntity<Void> changeOrderStat(@PathVariable Integer orderID,
			@RequestBody RequestsUpdate ordStatReq, @RequestHeader("Authorization") String code){
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		Order updOrder = os.changeOrderStat(orderID, ordStatReq.getStatus());
		if (updOrder == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
		}
		return ResponseEntity.noContent().build();
	}
	
	// Updating the product's retail price
	@PutMapping("/products/{prodCode}/price")
	public Product updateProdPrice(@PathVariable String prodCode,
			@RequestBody RequestsUpdate prodPriceReq, @RequestHeader("Authorization") String code) {
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		Product updProd = os.updateProductRP(prodCode, prodPriceReq.getUpdatedPrice());
		if (updProd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		return updProd;
	}
	
	// Request for getting the customer's total revenue
	@GetMapping("/customers/{custID}/revenue")
	public BigDecimal getCustRevenue(@PathVariable Integer custID, @RequestHeader("Authorization") String code) {
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return os.getCustRevenue(custID);
	}
	
	// Adding product from the wholesaler external API (MAIN ADD PRODUCT METHOD)
	@PostMapping("/wholesaler/products/{prodCode}")
    public ResponseEntity<Product> addProductFromWholesaler(
            @PathVariable String prodCode,
            @RequestHeader("Authorization") String code) {
        if(!authentication(code)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        
        Product newProd = ws.syncProductByCode(prodCode);
        if (newProd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in wholesaler");
        }
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/operator/products/" + newProd.getProdCode())
                .body(newProd);
    }
	
	// Adding product (Second option if the external database is down)
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product p,
			@RequestHeader("Authorization") String code){
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		Product newProduct = os.addProduct(p);
        if (newProduct == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product");
        }
        // if not null, it will be successfully added
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/operator/products/" + newProduct.getProdCode())
                .body(newProduct);
	}
	
	// Removing Product
	@DeleteMapping("/products/{prodCode}")
	public ResponseEntity<Void> removeProduct(@PathVariable String prodCode,
			@RequestHeader("Authorization") String code){
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		os.removeProduct(prodCode);
		return ResponseEntity.noContent().build();
	}
	
	// Adding product ON SALE
	@PutMapping("/products/{prodCode}/on-sale")
	public ResponseEntity<Product> addToSale(@PathVariable String prodCode,
			@RequestHeader("Authorization") String code){
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		Product p = os.addToSale(prodCode);
		if (p == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		return ResponseEntity.ok(p);
	}
	
	// Removing products from sale
	@PutMapping("/products/{prodCode}/not-sale")
	public ResponseEntity<Product> removeFromSale(@PathVariable String prodCode,
			@RequestHeader("Authorization") String code){
		if(!authentication(code)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		Product p = os.removeFromSale(prodCode);
		if (p == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		return ResponseEntity.ok(p);
	}
	
}

