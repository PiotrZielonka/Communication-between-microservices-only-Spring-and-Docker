package com.task.customer.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerDto {

  private Long id;

  @NotNull
  @Size(min = 0, max = 1000)
  private String firstName;

  @NotNull
  @Size(min = 0, max = 1000)
  private String surname;

  @NotNull
  @Size(min = 0, max = 1000)
  private String pesel;

  @NotNull
  private Long creditId;

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

    CustomerDto that = (CustomerDto) o;

    if (!id.equals(that.id)) {
      return false;
    }
    if (!firstName.equals(that.firstName)) {
      return false;
    }
    if (!surname.equals(that.surname)) {
      return false;
    }
    if (!pesel.equals(that.pesel)) {
      return false;
    }
    return creditId.equals(that.creditId);
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
    return "CustomerDto{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", surname='" + surname + '\''
        + ", pesel='" + pesel + '\''
        + ", creditId='" + creditId + '\''
        + '}';
  }
}
