package com.task.product.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.product.IntegrationTest;
import com.task.product.domain.Product;
import com.task.product.repository.ProductRepository;
import com.task.product.service.dto.ProductDto;
import com.task.product.service.mapper.ProductMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

@ActiveProfiles("test")
@IntegrationTest
@AutoConfigureMockMvc
public class ProductControllerTest {

  // Credit
  private static final String DEFAULT_CREDIT_NAME = "AAAAAAAAAA";

  // Product
  private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";

  private static final Integer DEFAULT_PRODUCT_VALUE = 123459;


  private static final String ENTITY_API_URL = "/products";

  private static final String ENTITY_API_URL_FIND_BY_CREDIT_ID = ENTITY_API_URL + "/{id}";


  @Autowired
  ProductRepository productRepository;

  @Autowired
  private MockMvc restProductMockMvc;

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private JdbcTemplate jdbc;

  Long creditIdOfsavedCredit;


  @BeforeEach
  public void initTest() {
    jdbc.update(
        "insert into credit (credit_name) values (?)", DEFAULT_CREDIT_NAME);

    creditIdOfsavedCredit = jdbc.queryForObject(
        "select id from credit where id=(select max(id) from credit)", Long.class);
  }

  @Test
  @Transactional
  void getProductByCreditId() throws Exception {
    // given
    jdbc.update(
        "insert into product (name, product_value, credit_id) values (?,?,?)",
        DEFAULT_PRODUCT_NAME, DEFAULT_PRODUCT_VALUE, creditIdOfsavedCredit);

    // when then
    restProductMockMvc.perform(get(ENTITY_API_URL_FIND_BY_CREDIT_ID, creditIdOfsavedCredit))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.name").value(DEFAULT_PRODUCT_NAME))
        .andExpect(jsonPath("$.productValue").value(DEFAULT_PRODUCT_VALUE))
        .andExpect(jsonPath("$.creditId").value(creditIdOfsavedCredit));
  }

  @Test
  @Transactional
  void getNonExistingProduct() throws Exception {
    // when then
    restProductMockMvc.perform(get(ENTITY_API_URL_FIND_BY_CREDIT_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void createProduct() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from product", int.class);

    ProductDto productDto = new ProductDto();
    productDto.setName(DEFAULT_PRODUCT_NAME);
    productDto.setProductValue(DEFAULT_PRODUCT_VALUE);
    productDto.setCreditId(creditIdOfsavedCredit);

    // when
    restProductMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDto)))
        .andExpect(status().isOk());

    // then
    List<Product> productList = (List<Product>) productRepository.findAll();
    assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
    Product testProduct = productList.get(productList.size() - 1);
    assertThat(testProduct.getId()).isNotNull();
    assertThat(testProduct.getName()).isEqualTo(DEFAULT_PRODUCT_NAME);
    assertThat(testProduct.getProductValue()).isEqualTo(DEFAULT_PRODUCT_VALUE);
    assertThat(testProduct.getCreditId()).isEqualTo(creditIdOfsavedCredit);
  }

  @Test
  @Transactional
  void createProductWithExistingId() throws Exception {
    // given
    jdbc.update(
        "insert into product (name, product_value, credit_id) values (?,?,?)",
        DEFAULT_PRODUCT_NAME, DEFAULT_PRODUCT_VALUE, creditIdOfsavedCredit);

    Long productIdOfsavedProduct = jdbc.queryForObject(
        "select id from product where id=(select max(id) from product)", Long.class);

    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from product", int.class);

    Product product = productRepository.findById(productIdOfsavedProduct).get();
    ProductDto productDto = productMapper.toDto(product);
    productDto.setId(productIdOfsavedProduct);
    productDto.setName(DEFAULT_PRODUCT_NAME);
    productDto.setProductValue(DEFAULT_PRODUCT_VALUE);
    productDto.setCreditId(creditIdOfsavedCredit);

    // when
    assertThrows(NestedServletException.class, () -> {
      restProductMockMvc.perform(post(ENTITY_API_URL)
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(productDto)))
          .andExpect(status().isBadRequest());
    });

    // then
    List<Product> productList = (List<Product>) productRepository.findAll();
    assertThat(productList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkNameIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    ProductDto productDto = new ProductDto();
    productDto.setName(null);
    productDto.setProductValue(DEFAULT_PRODUCT_VALUE);
    productDto.setCreditId(creditIdOfsavedCredit);

    // when
    restProductMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Product> productList = (List<Product>) productRepository.findAll();
    assertThat(productList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkProductValueIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    ProductDto productDto = new ProductDto();
    productDto.setName(DEFAULT_PRODUCT_NAME);
    productDto.setProductValue(null);
    productDto.setCreditId(creditIdOfsavedCredit);

    // when
    restProductMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Product> productList = (List<Product>) productRepository.findAll();
    assertThat(productList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkCreditIdIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    ProductDto productDto = new ProductDto();
    productDto.setName(DEFAULT_PRODUCT_NAME);
    productDto.setProductValue(DEFAULT_PRODUCT_VALUE);
    productDto.setCreditId(null);

    // when
    restProductMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Product> productList = (List<Product>) productRepository.findAll();
    assertThat(productList).hasSize(databaseSizeBeforeCreate);
  }
}
