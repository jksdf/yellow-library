/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;

/**
 * @author Matej Gallo
 */
@Entity
public class BookInstance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String bookState;

  private String version;

  @NotNull
  @Enumerated
  private BookAvailability availability;

  @NotNull
  @ManyToOne
  private Book book;

  public BookInstance() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBookState() {
    return this.bookState;
  }

  public void setBookState(String state) {
    this.bookState = state;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public BookAvailability getBookAvailability() {
    return this.availability;
  }

  public void setBookAvailability(BookAvailability availability) {
    this.availability = availability;
  }

  public Book getBook() {
    return this.book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BookInstance that = (BookInstance) o;

    if (!getBookState().equals(that.getBookState())) {
      return false;
    }
    if (getVersion() != null ? !getVersion().equals(that.getVersion())
        : that.getVersion() != null) {
      return false;
    }
    if (availability != that.availability) {
      return false;
    }
    return getBook().equals(that.getBook());

  }

  @Override
  public int hashCode() {
      return Objects.hash(getBookState(), getBookAvailability(), getBook());
    }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getBook().getName() + ":");
    sb.append("\n\tID: " + this.getId());
    sb.append("\n\tVersion: " + this.getVersion());
    sb.append("\n\tState: " + this.getBookState());
    sb.append("\n");
    return sb.toString();
  }

}
