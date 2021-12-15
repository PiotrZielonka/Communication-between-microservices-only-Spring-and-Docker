package com.task.credit.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreditDto {


  private Long id;

  @NotNull
  @Size(min = 0, max = 1000)
  private String creditName;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreditName() {
    return creditName;
  }

  public void setCreditName(String creditName) {
    this.creditName = creditName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CreditDto)) {
      return false;
    }

    CreditDto creditDto = (CreditDto) o;

    if (getId() != null ? !getId().equals(creditDto.getId()) : creditDto.getId() != null) {
      return false;
    }
    return getCreditName() != null ? getCreditName().equals(creditDto.getCreditName())
        : creditDto.getCreditName() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getCreditName() != null ? getCreditName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CreditDto{"
        + "id=" + id
        + ", creditName='" + creditName + '\''
        + '}';
  }

}
