package com.task.customer.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class Customer {

  @Id
  private Long id;

  @NotNull
  @Size(min = 0, max = 1000)
  private String firstName;

  @NotNull
  @Size(min = 0, max = 1000)
  private String surname;

  @NotNull
  @Size(min = 11, max = 11, message = "Pesel must have 11 characters")
  private String pesel;

  @NotNull
  private Long creditId;

  public Customer() {

  }

  public Customer(Long id, String firstName, String surname, String pesel, Long creditId) {
    this.id = id;
    this.firstName = firstName;
    this.surname = surname;
    this.pesel = pesel;
    this.creditId = creditId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getPesel() {
    return pesel;
  }

  public void setPesel(String pesel) {
    this.pesel = pesel;
  }

  public Long getCreditId() {
    return creditId;
  }

  public void setCreditId(Long creditId) {
    this.creditId = creditId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Customer customer = (Customer) o;

    if (!id.equals(customer.id)) {
      return false;
    }
    if (!firstName.equals(customer.firstName)) {
      return false;
    }
    if (!surname.equals(customer.surname)) {
      return false;
    }
    if (!pesel.equals(customer.pesel)) {
      return false;
    }
    return creditId.equals(customer.creditId);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + firstName.hashCode();
    result = 31 * result + surname.hashCode();
    result = 31 * result + pesel.hashCode();
    result = 31 * result + creditId.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Customer{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", surname='" + surname + '\''
        + ", pesel='" + pesel + '\''
        + ", creditId='" + creditId + '\''
        + '}';
  }
}
