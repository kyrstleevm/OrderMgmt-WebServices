package OrderMgmt.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import OrderMgmt.model.Product;
import OrderMgmt.repository.ProductRepository;

@Component
public class WholesalerService {
	private final String WHOLESALER_BASE_URL = "https://pmaier.eu.pythonanywhere.com/wss";
	private final RestTemplate rt = new RestTemplate(); 
	private final ObjectMapper om = new ObjectMapper();
	
	@Autowired
	private ProductRepository prodRepo;
	
	// [AI Collaborated]
	// Method that syncs the products of wholesaler API to the local database
	public Product syncProductByCode(String productCode) {
	    try {
	        // Start at root - Getting API's homepage for available links
	        String rootResponse = rt.getForObject(WHOLESALER_BASE_URL, String.class);
	        JsonNode rootNode = om.readTree(rootResponse);
	        
	        // Find the product link template from _links
	        JsonNode links = rootNode.path("_links");
	        String prodLink = null;
	        
	        // check if it really is the actual product link template from _links
	        if (links.has("product")) {
	            prodLink = links.path("product").path("href").asText();
	        } else {
	        	// fallback hardcoded link template
	        	prodLink = "/product/{code}";
	        }
	        
	        // Use the discovered template to build the URL (HATEOAS)
	        String productUrl = WHOLESALER_BASE_URL + prodLink.replace("{code}", productCode);
	        
	        // Get the product directly
	        Product product = getProductDetails(productUrl);
	        if (product != null) {
	        	Product saved = prodRepo.save(product);
	        	saved.setInStock(null);
	            return saved;
	        }
	        return null;
	        
	    } catch (Exception e) {
	        System.err.println("Error syncing product: " + productCode);
	        return null;
	    }
	}
	
	// Method that gets the stock of a product
	public Product getProductStock(String prodCode) {
		try {
	    	// Start at root endpoint
	    	String rootResponse = rt.getForObject(WHOLESALER_BASE_URL, String.class);
	        JsonNode rootNode = om.readTree(rootResponse);
	        
	        // Find the product link template from _links in the base URL
	        JsonNode links = rootNode.path("_links");
	        String prodLink = null;
	        
	     // check if it really is the actual product link template from _links
	        if (links.has("product")) {		// checks if it's in the link path
	        	prodLink = links.path("product").path("href").asText();
	        }
	        else {
		        // Get the product directly
	        	prodLink = "/product/{code}";
	        }
	        
	        // Use the link template to build HATEOAS URL
	        String prodURL = WHOLESALER_BASE_URL + prodLink.replace("{code}", prodCode);
	        
	        // get and save the product details from wholesaler
	        String prodJSON = rt.getForObject(prodURL, String.class);
	        JsonNode prodNODE = om.readTree(prodJSON);
	        
	        // obtaining values from the links path to local variables
	        String productCode = prodNODE.path("id").asText();
	        String desc = prodNODE.path("description").asText();
	        BigDecimal retailPrice = prodNODE.path("price").decimalValue();
	        int stock = prodNODE.path("in_stock").asInt();
	        
	        // Product object
	        Product p = new Product(productCode, desc, retailPrice);
	        p.setInStock(stock);
	        
	        return p;
	    }
		catch (Exception e) {
	        System.err.println("Error getting product: " + prodCode);
	        return null;
	    }
	}
	
	// Getting product details with the product URL as the argument
	private Product getProductDetails(String prodURL) {
		try {
			String prodJSON = rt.getForObject(prodURL, String.class);
			JsonNode prodNODE = om.readTree(prodJSON);
			
			String prodCode = prodNODE.path("id").asText();
			String desc = prodNODE.path("description").asText();
			BigDecimal rp = prodNODE.path("price").decimalValue();
			
			// Translating wholesaler API format to my application domain mode
			Product p = new Product(prodCode, desc, rp);
			p.setOnSaleStatus(true);
			return p;
		}
		catch(Exception e) {
			System.err.println("Error parsing product from: " + prodURL);
            return null;
		}
	}
	
}
