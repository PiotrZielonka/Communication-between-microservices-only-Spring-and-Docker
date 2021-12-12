package com.task.credit.controller;

import com.task.credit.controller.errors.BadRequestAlertException;
import com.task.credit.service.CreditService;
import com.task.credit.service.dto.CreditCustomerProductDto;
import com.task.credit.service.dto.CreditDto;
import com.task.customer.service.dto.CustomerDto;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CreditController {

  private final Logger log = LoggerFactory.getLogger(CreditController.class);

  private static final String ENTITY_NAME = "credit";

  private final CreditService creditService;

  RestTemplate restTemplate = new RestTemplate();


  public CreditController(CreditService creditService) {
    this.creditService = creditService;
  }

  @PostMapping("/credits")
  public ResponseEntity<CreditDto> createCredit(
      @RequestBody CreditCustomerProductDto creditCustomerProductDto) throws URISyntaxException {
    log.debug("REST request to save Credit Customer and Product : {}", creditCustomerProductDto);
    if (creditCustomerProductDto.getId() != null) {
      throw new BadRequestAlertException(
          "A new credit customer product cannot already have an ID", ENTITY_NAME, "idexists");
    }

    CreditDto result = creditService.save(creditCustomerProductDto);

    saveCustomerToCustomerMicroserviceThereIsCustomerTableCreated(
        creditCustomerProductDto, result);

    return ResponseEntity
        .created(new URI("/credits/" + result.getId()))
        .body(result);
  }

  private void saveCustomerToCustomerMicroserviceThereIsCustomerTableCreated(
      CreditCustomerProductDto creditCustomerProductDto, CreditDto result) {

    CustomerDto customerDto = creditCustomerProductDto.getCustomerDto();
    setCreditIdToCustomerDtoForRelationShipOneToOne(result, customerDto);
    restTemplate.postForObject("http://localhost:8081/customers", customerDto, String.class);
  }

  private void setCreditIdToCustomerDtoForRelationShipOneToOne(
      CreditDto result, CustomerDto customerDto) {

    customerDto.setCreditId(result.getId());
  }

  @GetMapping("/credits")
  public List<CreditCustomerProductDto> getCredits() {
    log.debug("REST request to get all Credits Customers and Products");
    return creditService.findAll();
  }

  @GetMapping("/onlyCredits")
  public List<CreditDto> getOnlyCredits() {
    log.debug("REST request to get only all Credits");
    return creditService.findAllOnlyCredits();
  }
}
