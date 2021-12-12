package com.task.product.service;

import com.task.product.service.dto.ProductDto;
import java.util.Optional;

public interface ProductService {

  Optional<ProductDto> findByCreditId(Long id);

  void save(ProductDto productDto);
}
