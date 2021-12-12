package com.task.product.service.impl;

import com.task.product.domain.Product;
import com.task.product.repository.ProductRepository;
import com.task.product.service.ProductService;
import com.task.product.service.dto.ProductDto;
import com.task.product.service.mapper.ProductMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;


  public ProductServiceImpl(
      ProductRepository productRepository,
      ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }


  @Override
  @Transactional
  public void save(ProductDto productDto) {
    log.debug("Request to save Product : {}", productDto);
    Product product = productMapper.toEntity(productDto);
    productRepository.save(product);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ProductDto> findByCreditId(Long id) {
    log.debug("Request to get Product by creditId : {}", id);
    return productRepository.findByCreditId(id)
        .map(productMapper::toDto);
  }
}
