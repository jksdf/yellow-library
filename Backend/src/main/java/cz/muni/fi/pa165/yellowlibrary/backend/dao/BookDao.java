package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

/**
 * @author Norbert Slivka
 */
public interface BookDao {

  /**
   * Retrieve the book.
   * @param id of the book requested.
   */
  Book getBookFromId(long id);

  /**
   * Retrieve all books.
   */
  List<Book> getAllBooks();

  /**
   * Remove the book.
   * @param book to be removed.
   */
  void remove(Book book);

  /**
   * Stores new book.
   * @param book to be saved.
   */
  void create(Book book);

  /**
   * Updates the stored book (according to the id).
   * @param book Updated book.
   */
  void update(Book book);
}
