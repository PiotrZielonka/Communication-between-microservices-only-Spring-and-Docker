package com.task.product.repository;

import com.task.product.domain.Product;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

  Optional<Product> findByCreditId(Long creditId);
}
