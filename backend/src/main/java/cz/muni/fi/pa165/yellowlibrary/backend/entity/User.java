/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;

/**
 * Entity user.
 *
 * @author Jozef Zivcic
 */
@Entity
@Table(name = "LibraryUser") // Name must not be "User" - it is a keyword
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String login;

  @NotNull
  private String passwordHash;

  @NotNull
  private String name;

  @NotNull
  private String address;

  @NotNull
  private BigDecimal totalFines;

  @OneToMany(mappedBy = "user")
  @NotNull
  private Set<Loan> loans;

  @Enumerated
  private UserType userType;

  public User() {
    loans = new HashSet<>();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
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

  public String getLogin() {
    return login;
  }

  public String getPasswordHash() {
    return passwordHash;
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

  /**
   * Checks whether user has role of employee.
   * @return True if user is employee, false otherwise.
   */
  public boolean isEmployee() {
    return userType == UserType.EMPLOYEE;
  }

  /**
   * Checks whether user has role of customer.
   * @return True if user is customer, false otherwise.
   */
  public boolean isCustomer() {
    return userType == UserType.CUSTOMER;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || !(o instanceof User))
      return false;

    User user = (User) o;

    return getLogin().equals(user.getLogin());

  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getLogin());
  }
}
