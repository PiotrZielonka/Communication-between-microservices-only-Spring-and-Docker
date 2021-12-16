package com.task.customer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.customer.IntegrationTest;
import com.task.customer.domain.Customer;
import com.task.customer.repository.CustomerRepository;
import com.task.customer.service.dto.CustomerDto;
import com.task.customer.service.mapper.CustomerMapper;
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
class CustomerControllerTest {

  // Credit
  private static final String DEFAULT_CREDIT_NAME = "AAAAAAAAAA";

  // Customer
  private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";

  private static final String DEFAULT_SURNAME = "AAAAAAAAAA";

  private static final String DEFAULT_PESEL = "12345678910";


  private static final String ENTITY_API_URL = "/customers";

  private static final String ENTITY_API_URL_FIND_BY_CREDIT_ID = ENTITY_API_URL + "/{id}";

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private MockMvc restCustomerMockMvc;

  @Autowired
  private CustomerMapper customerMapper;

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
  void getCustomerByCreditId() throws Exception {
    // given
    jdbc.update(
        "insert into customer (first_name, surname, pesel, credit_id) values (?,?,?,?)",
        DEFAULT_FIRST_NAME, DEFAULT_SURNAME, DEFAULT_PESEL, creditIdOfSavedCredit);

    Long idOfsavedCustomer = jdbc.queryForObject(
        "select id from customer where id=(select max(id) from customer)", Long.class);

    // when then
    restCustomerMockMvc.perform(get(ENTITY_API_URL_FIND_BY_CREDIT_ID, creditIdOfSavedCredit))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(idOfsavedCustomer))
        .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
        .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
        .andExpect(jsonPath("$.pesel").value(DEFAULT_PESEL))
        .andExpect(jsonPath("$.creditId").value(creditIdOfSavedCredit));
  }

  @Test
  @Transactional
  void getNonExistingCustomer() throws Exception {
    // when then
    restCustomerMockMvc.perform(get(ENTITY_API_URL_FIND_BY_CREDIT_ID, Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void createCustomer() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel(DEFAULT_PESEL);
    customerDto.setCreditId(creditIdOfSavedCredit);

    // when
    restCustomerMockMvc.perform(post(ENTITY_API_URL)
           .contentType(MediaType.APPLICATION_JSON)
           .content(TestUtil.convertObjectToJsonBytes(customerDto)))
        .andExpect(status().isOk());

    // then
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
    Customer testCustomer = customerList.get(customerList.size() - 1);
    assertThat(testCustomer.getId()).isNotNull();
    assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
    assertThat(testCustomer.getSurname()).isEqualTo(DEFAULT_SURNAME);
    assertThat(testCustomer.getPesel()).isEqualTo(DEFAULT_PESEL);
    assertThat(testCustomer.getCreditId()).isEqualTo(creditIdOfSavedCredit);
  }

  @Test
  @Transactional
  void createCustomerWithExistingId() throws Exception {
    // given
    jdbc.update(
        "insert into customer (first_name, surname, pesel, credit_id) values (?,?,?,?)",
        DEFAULT_FIRST_NAME, DEFAULT_SURNAME, DEFAULT_PESEL, creditIdOfSavedCredit);

    Long customerIdOfsavedCustomer = jdbc.queryForObject(
        "select id from customer where id=(select max(id) from customer)", Long.class);

    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    Customer customer = customerRepository.findById(customerIdOfsavedCustomer).get();
    CustomerDto customerDto = customerMapper.toDto(customer);
    customerDto.setId(customerIdOfsavedCustomer);
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel(DEFAULT_PESEL);
    customerDto.setCreditId(creditIdOfSavedCredit);

    // when
    assertThrows(NestedServletException.class, () -> {
      restCustomerMockMvc.perform(post(ENTITY_API_URL)
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(customerDto)));
    });

    // then
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFirstNameIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(null);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel(DEFAULT_PESEL);
    customerDto.setCreditId(creditIdOfSavedCredit);

    // when
    restCustomerMockMvc.perform(post(ENTITY_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.convertObjectToJsonBytes(customerDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkSurnameIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(null);
    customerDto.setPesel(DEFAULT_PESEL);
    customerDto.setCreditId(creditIdOfSavedCredit);

    // when
    restCustomerMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkPeselIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel(null);
    customerDto.setCreditId(creditIdOfSavedCredit);

    // when
    restCustomerMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkCreditIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel(DEFAULT_PESEL);
    customerDto.setCreditId(null);

    // when
    restCustomerMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkPeselElevenCharactersSizeIsRequierd() throws Exception {
    // given
    final int databaseSizeBeforeCreate = jdbc.queryForObject(
        "select count(*) from customer", int.class);

    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName(DEFAULT_FIRST_NAME);
    customerDto.setSurname(DEFAULT_SURNAME);
    customerDto.setPesel("123456789012");
    customerDto.setCreditId(creditIdOfSavedCredit);

    // when
    restCustomerMockMvc.perform(post(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerDto)))
        .andExpect(status().isBadRequest());

    // then
    List<Customer> customerList = (List<Customer>) customerRepository.findAll();
    assertThat(customerList).hasSize(databaseSizeBeforeCreate);
  }
}
