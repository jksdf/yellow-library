/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author Cokinova
 */
@Entity
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Temporal(TemporalType.DATE)
  private Date dateFrom;

  @Temporal(TemporalType.DATE)
  private Date returnDate;

  @NotNull
  private int loanLength;

  @NotNull
  private String loanState;

  @NotNull
  @ManyToOne
  private User user;

  @NotNull
  @ManyToOne
  private BookInstance book;

  private BigDecimal fine;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(Date dateFrom) {
    this.dateFrom = dateFrom;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public int getLoanLength() {
    return loanLength;
  }

  public void setLoanLength(int loanLength) {
    this.loanLength = loanLength;
  }

  public String getLoanState() {
    return loanState;
  }

  public void setLoanState(String loanState) {
    this.loanState = loanState;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public BookInstance getBook() {
    return book;
  }

  public void setBook(BookInstance book) {
    this.book = book;
  }

  public BigDecimal getFine() {
    return fine;
  }

  public void setFine(BigDecimal fine) {
    this.fine = fine;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Loan loan = (Loan) o;

    return getId().equals(loan.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
