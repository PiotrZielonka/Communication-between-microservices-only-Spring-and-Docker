package com.task.customer.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.task.customer.CustomerApplication;
import com.task.customer.domain.Customer;
import com.task.customer.repository.CustomerRepository;
import com.task.customer.service.dto.CustomerDto;
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
@SpringBootTest(classes = CustomerApplication.class)
public class CustomerServiceImplTest {

  // Credit
  private static final String DEFAULT_CREDIT_NAME = "AAAAAAAAAA";

  // Customer
  private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";

  private static final String DEFAULT_SURNAME = "AAAAAAAAAA";

  private static final String DEFAULT_PESEL = "12345678910";

  @Autowired
  private CustomerServiceImpl customerServiceImpl;

  @Autowired
  private CustomerRepository customerRepository;

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
  public void shouldFindCustomerByCreditId() throws Exception {
    // given
    jdbc.update(
        "insert into customer (first_name, surname, pesel, credit_id) values (?,?,?,?)",
        DEFAULT_FIRST_NAME, DEFAULT_SURNAME, DEFAULT_PESEL, creditIdOfSavedCredit);

    Long idOfSavedCustomer = jdbc.queryForObject(
        "select id from customer where id=(select max(id) from customer)", Long.class);

    // when
    Optional<CustomerDto> testCustomer
        = customerServiceImpl.findByCreditId(creditIdOfSavedCredit);

    // then
    assertThat(testCustomer.get().getId()).isEqualTo(idOfSavedCustomer);
    assertThat(testCustomer.get().getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    assertThat(testCustomer.get().getSurname()).isEqualTo(DEFAULT_SURNAME);
    assertThat(testCustomer.get().getPesel()).isEqualTo(DEFAULT_PESEL);
    assertThat(testCustomer.get().getCreditId()).isEqualTo(creditIdOfSavedCredit);
  }

  @Test
  @Transactional
  public void shouldSaveCustomer() throws Exception {
    // given
    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel(DEFAULT_PESEL);
    customerDto.setCreditId(creditIdOfSavedCredit);

    final int customerDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from customer", int.class);
    final int creditDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from credit", int.class);

    // when
    customerServiceImpl.save(customerDto);
    Long idOfSavedCustomer = jdbc.queryForObject(
        "select id from customer where id=(select max(id) from customer)", Long.class);

    // then
    assertThat(creditDatabaseSizeBeforeSave).isEqualTo(creditDatabaseSizeBeforeSave);
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(customerDatabaseSizeBeforeSave + 1);
    Customer testCustomer = customerList.get(customerList.size() - 1);
    assertThat(testCustomer.getId()).isEqualTo(idOfSavedCustomer);
    assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    assertThat(testCustomer.getSurname()).isEqualTo(DEFAULT_SURNAME);
    assertThat(testCustomer.getCreditId()).isEqualTo(creditIdOfSavedCredit);
  }

  @Test
  public void shouldNotSaveCustomerTwiceBecauseInOneToOneRelationship() throws Exception {
    // given
    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel(DEFAULT_PESEL);
    customerDto.setCreditId(creditIdOfSavedCredit);

    final int customerDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from customer", int.class);
    final int creditDatabaseSizeBeforeSave = jdbc.queryForObject(
        "select count(*) from credit", int.class);

    customerServiceImpl.save(customerDto);
    final Long idOfSavedCustomer = jdbc.queryForObject(
        "select id from customer where id=(select max(id) from customer)", Long.class);

    // when then
    assertThrows(DbActionExecutionException.class, () -> {
      customerServiceImpl.save(customerDto);
    });

    assertThat(creditDatabaseSizeBeforeSave).isEqualTo(creditDatabaseSizeBeforeSave);
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(customerDatabaseSizeBeforeSave + 1);
    Customer testCustomer = customerList.get(customerList.size() - 1);
    assertThat(testCustomer.getId()).isEqualTo(idOfSavedCustomer);
    assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    assertThat(testCustomer.getSurname()).isEqualTo(DEFAULT_SURNAME);
    assertThat(testCustomer.getCreditId()).isEqualTo(creditIdOfSavedCredit);
  }
}
