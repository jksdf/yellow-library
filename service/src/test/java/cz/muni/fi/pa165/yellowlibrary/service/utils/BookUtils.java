package cz.muni.fi.pa165.yellowlibrary.service.utils;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by cokin on 25.11.2016.
 */
public class BookUtils {

  public static void assertDeepEquals(Book book1, Book book2) {
    assertThat(book1.getId()).isEqualTo(book2.getId());
    assertThat(book1.getIsbn()).isEqualTo(book2.getIsbn());
    assertThat(book1.getAuthor()).isEqualTo(book2.getAuthor());
    assertThat(book1.getDepartment()).isEqualTo(book2.getDepartment());
    assertThat(book1.getName()).isEqualTo(book2.getName());
    assertThat(book1.getPages()).isEqualTo(book2.getPages());
    assertThat(book1.getDescription()).isEqualTo(book2.getDescription());
    assertThat(book1.getBookInstances()).isEqualTo(book2.getBookInstances());
  }
}
