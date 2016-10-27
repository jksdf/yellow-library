package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * @author Norbert Slivka
 */
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String isbn;
  private String description;

  @NotNull
  private String name;
  private String author;
  private Integer pages;

  @OneToMany
  private Set<BookInstance> bookInstances;

  public Book() {
    bookInstances = new HashSet<>();
  }

  public Book(Long id) {
    this();
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }

  public Set<BookInstance> getBookInstances() {
    return bookInstances;
  }

  public void setBookInstances(Set<BookInstance> bookInstances) {
    this.bookInstances = bookInstances;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Book)) {
      return false;
    }
    Book other = (Book) o;
    if (isbn == null && other.isbn == null) {
      return Objects.equals(author, other.author)
          && Objects.equals(name, other.name);
    }
    return Objects.equals(isbn, ((Book) o).isbn);
  }

  @Override
  public int hashCode() {
    if (isbn == null) {
      return Objects.hash(author, name);
    }
    return isbn.hashCode();
  }
}
