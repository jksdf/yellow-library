/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvaliability;

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
  private BookAvaliability avaliability;

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

  public BookAvaliability getBookAvaliability() {
    return this.avaliability;
  }

  public void setBookAvailability(BookAvaliability availability) {
    this.avaliability = availability;
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
    if (avaliability != that.avaliability) {
      return false;
    }
    return getBook().equals(that.getBook());

  }

  @Override
  public int hashCode() {
    int result = getBookState().hashCode();
    result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
    result = 31 * result + avaliability.hashCode();
    result = 31 * result + getBook().hashCode();
    return result;
  }
}
