package OrderMgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import OrderMgmt.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
