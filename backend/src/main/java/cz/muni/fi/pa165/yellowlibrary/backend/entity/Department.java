package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Norbert Slivka
 */
@Entity
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 3, max = 3)
  @Column(unique = true)
  private String shortName;

  @NotNull
  private String name;

  @OneToMany(mappedBy = "department")
  private List<Book> books;

  public Department() {
    books = new ArrayList<>();
  }

  public Department(Long id) {
    this();
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Department)) {
      return false;
    }
    Department that = (Department) o;
    return Objects.equals(that.getShortName(), getShortName());

  }

  @Override
  public int hashCode() {
    return Objects.hash(getShortName());
  }
}
