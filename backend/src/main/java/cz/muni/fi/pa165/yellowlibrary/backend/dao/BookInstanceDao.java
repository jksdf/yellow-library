package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;

/**
 * Created by reyvateil on 26.10.2016.
 * @author Matej Gallo
 */

/**
 * If {@code null} argument is passed to any method
 * a {@code NullPointerException} will be thrown
 */
public interface BookInstanceDao {

  /**
   * Retrieve a book instance by its ID
   * @param id of a book instance to be retrieved
   * @return BookInstance entity
   */
  BookInstance findById(Long id);

  /**
   * Remove an instance of a book
   * @param bookInstance to be removed
   */
  void deleteBookInstance(BookInstance bookInstance);

  /**
   * Create new instance of a book
   * @param bookInstance to be created
   * @throws {@code ConstraintViolationException} if a required
   * field of a {@code BookInstance} is set to {@code null}
   */
  void createBookInstance(BookInstance bookInstance);

  /**
   * Change the values of a bookInstance
   * @param bookInstance to be changed
   * @throws {@code ConstraintViolationException} if a required
   * field of a {@code BookInstance} is set to {@code null}
   */
  void updateBookInstance(BookInstance bookInstance);
}
