package OrderMgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import OrderMgmt.model.Product;
public interface ProductRepository extends JpaRepository<Product, String> {

	List<Product> findByOnSaleTrue();
	// findAll, save(), deleteById() is all on JPA Repository
}
