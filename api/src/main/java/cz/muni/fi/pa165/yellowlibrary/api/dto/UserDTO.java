package cz.muni.fi.pa165.yellowlibrary.api.dto;


import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cz.muni.fi.pa165.yellowlibrary.api.enums.UserType;

/**
 * @author Jozef Zivcic
 */
public class UserDTO {

  @NotNull
  private Long id;

  @Size(min = 1)
  private String name;

  @Size(min = 1)
  private String login;

  @Size(min=1)
  private String passwordHash;

  @NotNull
  private String address;

  @NotNull
  private BigDecimal totalFines;

  private UserType userType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public BigDecimal getTotalFines() {
    return totalFines;
  }

  public void setTotalFines(BigDecimal totalFines) {
    this.totalFines = totalFines;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public boolean isEmployee() {
    return getUserType().equals(UserType.EMPLOYEE);
  }

  public boolean isCustomer() {
    return getUserType().equals(UserType.CUSTOMER);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof UserDTO)) {
      return false;
    }

    UserDTO that = (UserDTO) o;

    return Objects.equals(getName(), that.getName()) &&
        Objects.equals(getLogin(), that.getLogin()) &&
        Objects.equals(getAddress(), that.getAddress()) &&
        Objects.equals(getTotalFines(), that.getTotalFines()) &&
        Objects.equals(getUserType(), that.getUserType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getLogin(), getAddress(), getTotalFines(), getUserType());
  }
}
