package OrderMgmt;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import OrderMgmt.repository.CustomerRepository;
import OrderMgmt.model.Customer;

@SpringBootApplication
public class OrderMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMgmtApplication.class, args);
	}
	
	// 3 hardcoded customers
	@Bean
	public String init(CustomerRepository custRepo) {
		try {
			// Hardcoding 3 customers
			Customer c1 = new Customer();
			c1.setCustID(1);
			c1.setName("Mackenzie Anderson");
			c1.setEmail("m.anderson@outlook.com");
			c1.setAdd("Isle of Skye");
			
			Customer c2 = new Customer();
			c2.setCustID(2);
			c2.setName("Ava Escobar");
			c2.setEmail("a.escobar@outlook.com");
			c2.setAdd("Stirling");
			
			Customer c3 = new Customer();
			c3.setCustID(3);
			c3.setName("Raylan Gallegos");
			c3.setEmail("r.gallegos@outlook.com");
			c3.setAdd("Edinburgh Castle");
			
			custRepo.save(c1);
			custRepo.save(c2);
			custRepo.save(c3);
			
		} 
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return "Customers added successfully";
	}
}
