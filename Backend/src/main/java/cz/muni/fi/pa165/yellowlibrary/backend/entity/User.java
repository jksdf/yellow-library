/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Jozef Zivcic
 */
@Entity
@Table(name = "LibraryUser") // Name must not be "User" - it is a keyword
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotNull
  private String name;

  @NotNull
  private String address;

  @NotNull
  private BigDecimal totalFines;

  @OneToMany(mappedBy = "user")
  private Set<Loan> loans;

  @Enumerated
  private UserType userType;

  public User() {
    loans = new HashSet<>();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setTotalFines(BigDecimal totalFines) {
    this.totalFines = totalFines;
  }

  public void setLoans(Set<Loan> loans) {
    this.loans = loans;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public BigDecimal getTotalFines() {
    return totalFines;
  }

  public Set<Loan> getLoans() {
    return Collections.unmodifiableSet(loans);
  }

  public UserType getUserType() {
    return userType;
  }

  public boolean addLoan(Loan loan) {
    return loans.add(loan);
  }

  public boolean removeLoan(Loan loan) {
    return loans.remove(loan);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || !(o instanceof User))
      return false;

    User user = (User) o;

    if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null)
      return false;

    if (getAddress() != null ? !getAddress().equals(user.getAddress())
        : user.getAddress() != null)
      return false;

    if (getTotalFines() != null ? !getTotalFines().equals(user.getTotalFines())
        : user.getTotalFines() != null)
      return false;

    if (getLoans() != null ? !getLoans().equals(user.getLoans()) : user.getLoans() != null)
      return false;

    return getUserType() == user.getUserType();

  }

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 1;
    result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 1);
    result = 31 * result + (getTotalFines() != null ? getTotalFines().hashCode() : 1);
    result = 31 * result + (getLoans() != null ? getLoans().hashCode() : 1);
    result = 31 * result + (getUserType() != null ? getUserType().hashCode() : 1);
    return result;
  }
}
