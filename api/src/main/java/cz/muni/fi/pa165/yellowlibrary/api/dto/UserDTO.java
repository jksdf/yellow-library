package cz.muni.fi.pa165.yellowlibrary.api.dto;


import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cz.muni.fi.pa165.yellowlibrary.api.enums.UserType;

/**
 * Created by jozef on 21.11.2016.
 */
public class UserDTO {

  @NotNull
  private Long id;

  @Size(min = 1)
  private String name;

  @Size(min = 1)
  private String login;

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || !(o instanceof UserDTO))
      return false;

    UserDTO userDTO = (UserDTO) o;

    if (getName() != null ? !getName().equals(userDTO.getName()) : userDTO.getName() != null)
      return false;
    if (getLogin() != null ? !getLogin().equals(userDTO.getLogin()) : userDTO.getLogin() != null)
      return false;
    if (getAddress() != null ? !getAddress().equals(userDTO.getAddress())
        : userDTO.getAddress() != null)
      return false;
    if (getTotalFines() != null ? !getTotalFines().equals(userDTO.getTotalFines())
        : userDTO.getTotalFines() != null)
      return false;
    return getUserType() == userDTO.getUserType();
  }

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
    result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
    result = 31 * result + (getTotalFines() != null ? getTotalFines().hashCode() : 0);
    result = 31 * result + (getUserType() != null ? getUserType().hashCode() : 0);
    return result;
  }
}
