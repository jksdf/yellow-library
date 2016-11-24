package cz.muni.fi.pa165.yellowlibrary.service;

import java.util.List;
import java.util.Set;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

/**
 * @author Norbert Slivka
 */
public interface BookService {

  /**
   * Adds new book to the system.
   */
  void addBook(Book book);

  /**
   * Finds the book in the system by specified ID.
   *
   * @return Book if found, {@code null} otherwise.
   */
  Book getBook(long id);

  /**
   * Retrieves all books from the system.
   */
  List<Book> getAllBooks();

  /**
   * Retrieves all the books that have specified property.
   *
   * If the value of a parameter is {@code null}, the property is not used as a condition.
   * Otherwise, string values are searched as substring and department should contain all department
   * IDs to be searched for.
   */
  List<Book> searchBooks(String author, String name, String description, String isbn,
                         Set<Long> departments);

  /**
   * Edits the book that is already in the system.
   */
  void editBook(Book book);
}
