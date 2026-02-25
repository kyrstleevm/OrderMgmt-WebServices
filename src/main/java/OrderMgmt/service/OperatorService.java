package OrderMgmt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import OrderMgmt.model.*;

public interface OperatorService {
		// get/view all orders
		List<Map<String, Object>> viewAllOrders();
		
		// view all products
		List<Product> getAllProducts();
		
		// Change order status
		Order changeOrderStat(Integer orderID, Status stat);
		
		// Updating retail prices
		Product updateProductRP(String prodCode, BigDecimal newRP);
		
		// Getting customer's total revenue
		BigDecimal getCustRevenue(Integer custID);
		
		//add or remove products from database
		Product addProduct(Product prod);
		public void removeProduct(String prodCode);
		
		//add or remove products from sale
		Product addToSale(String prodCode);
		Product removeFromSale(String prodCode);
}
