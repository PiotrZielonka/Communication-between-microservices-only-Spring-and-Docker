package com.task.credit.controller;

import com.task.credit.controller.errors.IdInDtoExist;
import com.task.credit.controller.errors.ValidationProblemWithCustomer;
import com.task.credit.controller.errors.ValidationProblemWithProduct;
import com.task.credit.repository.CreditRepository;
import com.task.credit.service.CreditService;
import com.task.credit.service.dto.CreditCustomerProductDto;
import com.task.credit.service.dto.CreditDto;
import com.task.customer.service.dto.CustomerDto;
import com.task.product.service.dto.ProductDto;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class CreditController {

  private final Logger log = LoggerFactory.getLogger(CreditController.class);

  private final CreditService creditService;

  private final CreditRepository creditRepository;

  private final JdbcTemplate jdbc;

  RestTemplate restTemplate = new RestTemplate();


  public CreditController(CreditService creditService,
      CreditRepository creditRepository, JdbcTemplate jdbc) {
    this.creditService = creditService;
    this.creditRepository = creditRepository;
    this.jdbc = jdbc;
  }

  @PostMapping("/credits")
  public ResponseEntity<CreditDto> createCredit(@Valid @RequestBody
      CreditCustomerProductDto creditCustomerProductDto) throws URISyntaxException {
    log.debug("REST request to save Credit Customer and Product : {}", creditCustomerProductDto);
    if (creditCustomerProductDto.getId() != null) {
      throw new IdInDtoExist();
    }

    CreditDto result = creditService.save(creditCustomerProductDto);

    saveCustomerToCustomerMicroserviceThereIsCustomerTableLogic(
        creditCustomerProductDto, result);

    saveProductToProductMicroserviceThereIsProductTableLogic(
        creditCustomerProductDto, result);

    return ResponseEntity
        .created(new URI("/credits/" + result.getId()))
        .body(result);
  }

  private void saveCustomerToCustomerMicroserviceThereIsCustomerTableLogic(
      CreditCustomerProductDto creditCustomerProductDto, CreditDto result) {

    CustomerDto customerDto = creditCustomerProductDto.getCustomerDto();
    setCreditIdToCustomerDtoForRelationShipOneToOne(result, customerDto);

    try {
      restTemplate.postForObject("http://localhost:8081/customers", customerDto, CustomerDto.class);

    } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerException) {
      if (HttpStatus.BAD_REQUEST.equals(httpClientOrServerException.getStatusCode())) {
        creditRepository.deleteById(result.getId());
        throw new ValidationProblemWithCustomer();
      }
    }

  }

  private void setCreditIdToCustomerDtoForRelationShipOneToOne(
      CreditDto result, CustomerDto customerDto) {

    customerDto.setCreditId(result.getId());
  }

  private void saveProductToProductMicroserviceThereIsProductTableLogic(
      CreditCustomerProductDto creditCustomerProductDto, CreditDto result) {

    ProductDto productDto = creditCustomerProductDto.getProductDto();
    setCreditIdToProductDtoForRelationShipOneToOne(result, productDto);

    try {
      restTemplate.postForObject("http://localhost:8082/products", productDto, ProductDto.class);
    } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerException) {
      if (HttpStatus.BAD_REQUEST.equals(httpClientOrServerException.getStatusCode())) {
        jdbc.update("delete from customer where credit_id = ?", result.getId());
        creditRepository.deleteById(result.getId());
        throw new ValidationProblemWithProduct();
      }
    }
  }

  private void setCreditIdToProductDtoForRelationShipOneToOne(
      CreditDto result, ProductDto productDto) {

    productDto.setCreditId(result.getId());
  }

  @GetMapping("/credits")
  public List<CreditCustomerProductDto> getCredits() {
    log.debug("REST request to get all Credits Customers and Products");
    return creditService.findAll();
  }
}
