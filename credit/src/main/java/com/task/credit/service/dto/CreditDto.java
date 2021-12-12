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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CreditDto creditDto = (CreditDto) o;

    if (!id.equals(creditDto.id)) {
      return false;
    }
    return creditName.equals(creditDto.creditName);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + creditName.hashCode();
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
