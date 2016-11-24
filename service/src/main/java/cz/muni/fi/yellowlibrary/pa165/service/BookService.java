package cz.muni.fi.yellowlibrary.pa165.service;

import java.util.List;
import java.util.Set;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

/**
 * @author Norbert Slivka
 */
public interface BookService {
  void addBook(Book book);

  List<Book> getAllBooks();

  List<Book> searchBooks(String author, String name, String description, String isbn,
                         Set<Long> departments);

  void editBook(Book book);
}
