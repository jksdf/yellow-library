package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Norbert Slivka
 */
public class BookSearchDTO {
  private String name;
  private String author;
  private String description;
  private String isbn;
  private Set<Long> departmentIds;

  public BookSearchDTO() {
    departmentIds = new HashSet<>();
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public void setDepartmentIds(Set<Long> departmentIds) {
    this.departmentIds = departmentIds;
  }

  public Set<Long> getDepartmentIds() {
    return departmentIds;
  }
}
