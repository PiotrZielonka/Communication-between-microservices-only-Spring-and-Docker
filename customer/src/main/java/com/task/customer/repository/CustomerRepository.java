package com.task.customer.repository;

import com.task.customer.domain.Customer;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

  Optional<Customer> findByCreditId(Long creditId);
}
