package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cz.muni.fi.pa165.yellowlibrary.api.dto.annotations.IsIsbn;

/**
 * @author Norbert Slivka
 */
public class BookCreateDTO {
  private Long id;
  @NotNull
  @Size(min = 1, message = "{Size.book.name}")
  private String name;
  @IsIsbn
  private String isbn;
  private String description;
  private String author;
  @NotNull(message = "{NotNull.book.pages}")
  @Min(value = 0, message = "{Min.book.pages}")
  private Integer pages;
  @NotNull
  private Long departmentId;

  public BookCreateDTO() {
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

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BookCreateDTO)) {
      return false;
    }
    BookCreateDTO other = (BookCreateDTO) o;
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
        ", id='" + getId() + '\'' +
        ", name='" + name + '\'' +
        ", isbn='" + isbn + '\'' +
        ", description='" + description + '\'' +
        ", author='" + author + '\'' +
        ", pages=" + pages +
        ", department=" + departmentId +
        '}';
  }
}
