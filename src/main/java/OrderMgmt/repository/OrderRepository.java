package OrderMgmt.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import OrderMgmt.model.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	List<Order> findByCustID(Integer custID);
	
	// [AI Collaborated]
	// Calculating customer revenue
	@Query("SELECT SUM(p.retailPrice * o.quantity) " +
		       "FROM Order o, Product p " +
		       "WHERE o.prodCode = p.prodCode " +
		       "AND o.custID = :custID " +
		       "AND o.orderStatus != 'CANCELLED'")
			BigDecimal calcCustRevenue(@Param("custID") Integer custID);
}
