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
  @Size(min = 11, max = 11, message = "Pesel must have 11 characters")
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
    if (!(o instanceof CustomerDto)) {
      return false;
    }

    CustomerDto that = (CustomerDto) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    if (getFirstName() != null ? !getFirstName().equals(that.getFirstName())
        : that.getFirstName() != null) {
      return false;
    }
    if (getSurname() != null ? !getSurname().equals(that.getSurname())
        : that.getSurname() != null) {
      return false;
    }
    if (getPesel() != null ? !getPesel().equals(that.getPesel()) : that.getPesel() != null) {
      return false;
    }
    return getCreditId() != null ? getCreditId().equals(that.getCreditId())
        : that.getCreditId() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
    result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
    result = 31 * result + (getPesel() != null ? getPesel().hashCode() : 0);
    result = 31 * result + (getCreditId() != null ? getCreditId().hashCode() : 0);
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
