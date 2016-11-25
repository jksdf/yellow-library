package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Norbert Slivka
 */
public class BookDTO {
  private Long id;
  @NotNull
  private String name;
  private String isbn;
  private String description;
  private String author;
  @NotNull
  @Min(0)
  private Integer pages;
  @NotNull
  private DepartmentDTO department;

  public BookDTO() {
  }

  public BookDTO(Long id) {
    this();
    this.id = id;
  }

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

  public DepartmentDTO getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentDTO department) {
    this.department = department;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BookDTO)) {
      return false;
    }
    BookDTO other = (BookDTO) o;
    if (getIsbn() == null && other.getIsbn() == null) {
      return Objects.equals(getName(), other.getName()) &&
          Objects.equals(getAuthor(), other.getAuthor());
    }
    return Objects.equals(this.getIsbn(), other.getIsbn());
  }

  @Override
  public int hashCode() {
    if (getIsbn() == null) {
      return Objects.hash(getName(), getAuthor());
    }
    return getIsbn().hashCode();
  }

  @Override
  public String toString() {
    return "BookDTO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", isbn='" + isbn + '\'' +
        ", description='" + description + '\'' +
        ", author='" + author + '\'' +
        ", pages=" + pages +
        ", department=" + department +
        '}';
  }
}
