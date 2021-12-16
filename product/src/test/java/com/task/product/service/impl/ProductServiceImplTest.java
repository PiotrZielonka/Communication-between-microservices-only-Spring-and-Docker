package com.task.product.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.task.product.ProductApplication;
import com.task.product.domain.Product;
import com.task.product.repository.ProductRepository;
import com.task.product.service.dto.ProductDto;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(classes = ProductApplication.class)
class ProductServiceImplTest {

  // Credit
  private static final String DEFAULT_CREDIT_NAME = "AAAAAAAAAA";

  // Product
  private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";

  private static final Integer DEFAULT_PRODUCT_VALUE = 123459;

  @Autowired
  private ProductServiceImpl productServiceImpl;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private JdbcTemplate jdbc;

  private Long creditIdOfSavedCredit;

  @BeforeEach
  public void initTest() {
    jdbc.update(
        "insert into credit (credit_name) values (?)", DEFAULT_CREDIT_NAME);

    creditIdOfSavedCredit = jdbc.queryForObject(
        "select id from credit where id=(select max(id) from credit)", Long.class);
  }


  @Test
  @Transactional
  void shouldFindProductByCreditId() throws Exception {
    // given
    jdbc.update(
        "insert into product (name, product_value, credit_id) values (?,?,?)",
        DEFAULT_PRODUCT_NAME, DEFAULT_PRODUCT_VALUE, creditIdOfSavedCredit);

    Long idOfSavedProduct = jdbc.queryForObject(
        "select id from product where id=(select max(id) from product)", Long.class);

    // when
    Optional<ProductDto> testProduct
        = productServiceImpl.findByCreditId(creditIdOfSavedCredit);

    // then
    assertThat(testProduct.get().getId()).isEqualTo(idOfSavedProduct);
    assertThat(testProduct.get().getName()).isEqualTo(DEFAULT_PRODUCT_NAME);
    assertThat(testProduct.get().getProductValue()).isEqualTo(DEFAULT_PRODUCT_VALUE);
    assertThat(testProduct.get().getCreditId()).isEqualTo(creditIdOfSavedCredit);
  }

  @Test
  @Transactional
  void shouldSaveProduct() throws Exception {
    // given
    ProductDto productDto = new ProductDto();
    productDto.setName(DEFAULT_PRODUCT_NAME);
    productDto.setProductValue(DEFAULT_PRODUCT_VALUE);
    productDto.setCreditId(creditIdOfSavedCredit);

    final int productDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from product", int.class);
    final int creditDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from credit", int.class);

    // when
    productServiceImpl.save(productDto);
    Long idOfSavedProduct = jdbc.queryForObject(
        "select id from product where id=(select max(id) from product)", Long.class);

    // then
    final int creditDatabaseSizeAfterSave = jdbc.queryForObject(
        "select count(*) from credit", int.class);
    assertThat(creditDatabaseSizeBeforeSave).isEqualTo(creditDatabaseSizeAfterSave);
    List<Product> productList = (List<Product>) productRepository.findAll();
    assertThat(productList).hasSize(productDatabaseSizeBeforeSave + 1);
    Product testProduct = productList.get(productList.size() - 1);
    assertThat(testProduct.getId()).isEqualTo(idOfSavedProduct);
    assertThat(testProduct.getName()).isEqualTo(DEFAULT_PRODUCT_NAME);
    assertThat(testProduct.getProductValue()).isEqualTo(DEFAULT_PRODUCT_VALUE);
    assertThat(testProduct.getCreditId()).isEqualTo(creditIdOfSavedCredit);
  }

  @Test
  void shouldNotSaveProductTwiceBecauseInOneToOneRelationship() throws Exception {
    // given
    ProductDto productDto = new ProductDto();
    productDto.setName(DEFAULT_PRODUCT_NAME);
    productDto.setProductValue(DEFAULT_PRODUCT_VALUE);
    productDto.setCreditId(creditIdOfSavedCredit);
    productDto.setCreditId(creditIdOfSavedCredit);

    final int productDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from product", int.class);
    final int creditDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from credit", int.class);

    productServiceImpl.save(productDto);
    final Long idOfSavedProduct = jdbc.queryForObject(
        "select id from product where id=(select max(id) from product)", Long.class);

    // when then
    assertThrows(DbActionExecutionException.class, () -> {
      productServiceImpl.save(productDto);
    });

    final int creditDatabaseSizeAfterSave = jdbc.queryForObject(
        "select count(*) from credit", int.class);
    assertThat(creditDatabaseSizeBeforeSave).isEqualTo(creditDatabaseSizeAfterSave);
    List<Product> productList = (List<Product>) productRepository.findAll();
    assertThat(productList).hasSize(productDatabaseSizeBeforeSave + 1);
    Product testProduct = productList.get(productList.size() - 1);
    assertThat(testProduct.getId()).isEqualTo(idOfSavedProduct);
    assertThat(testProduct.getName()).isEqualTo(DEFAULT_PRODUCT_NAME);
    assertThat(testProduct.getProductValue()).isEqualTo(DEFAULT_PRODUCT_VALUE);
    assertThat(testProduct.getCreditId()).isEqualTo(creditIdOfSavedCredit);
  }
}
