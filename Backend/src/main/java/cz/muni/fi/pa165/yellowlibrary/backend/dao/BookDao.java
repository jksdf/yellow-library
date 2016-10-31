package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import java.util.List;

import javax.validation.ConstraintViolationException;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

/**
 * @author Norbert Slivka
 */
public interface BookDao {

  /**
   * Retrieve the book.
   * @param id of the book requested.
   * @return retrieved book or {@code null}, if there is no book with the ID.
   */
  Book getBookFromId(long id);

  /**
   * Retrieve all books.
   */
  List<Book> getAllBooks();

  /**
   * Remove the book.
   * @param book to be removed.
   * @throws NullPointerException if the {@code book} parameter is {@code null}.
   */
  void remove(Book book);

  /**
   * Stores new book.
   * @param book to be saved.
   * @throws NullPointerException if the {@code book} is {@code null}.
   * @throws ConstraintViolationException if {@code book} has {@code null} department, name,
   *         number of pages or negative number of pages.
   */
  void create(Book book);

  /**
   * Updates the stored book (according to the ID).
   * @param book Updated book.
   * @throws NullPointerException if the {@code book} is {@code null}.
   * @throws ConstraintViolationException if {@code book} has {@code null} department, name,
   *         number of pages or negative number of pages.
   */
  void update(Book book);
}
