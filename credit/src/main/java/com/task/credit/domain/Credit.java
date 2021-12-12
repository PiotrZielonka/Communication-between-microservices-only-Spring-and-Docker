package com.task.credit.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class Credit {

  @Id
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

  public Credit() {

  }

  public Credit(Long id, String creditName) {
    this.id = id;
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

    Credit credit = (Credit) o;

    if (!id.equals(credit.id)) {
      return false;
    }
    return creditName.equals(credit.creditName);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + creditName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Credit{"
        + "id=" + id
        + ", creditName='" + creditName + '\''
        + '}';
  }
}
