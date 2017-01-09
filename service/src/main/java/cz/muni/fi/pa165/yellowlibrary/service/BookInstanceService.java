package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;

/**
 * Service layer for Book Instance
 *
 * @author Matej Gallo
 */

@Service
public interface BookInstanceService {

  /**
   * Creating new book instance
   * @param bookInstance BookInstance
   * @return BookInstance created
   */
  BookInstance addBookInstance(BookInstance bookInstance);

  /**
   * Deletes a book instance
   * @param bookInstance BookInstance
   */
  void deleteBookInstance(BookInstance bookInstance);

  /**
   * Sets the book instance parent book
   * @param bookInstance BookInstance
   * @param book Book
   */
  void setBook(BookInstance bookInstance, Book book);

  /**
   * Changes the book instance state
   * @param bookInstance BookInstance
   * @param newState String
   */
  void changeState(BookInstance bookInstance, String newState);

  /**
   * Changes the book instance availability
   * @param bookInstance BookInstance
   * @param newAvailability BookAvailability
   */
  void changeAvailability(BookInstance bookInstance, BookAvailability newAvailability);

  /**
   * Retrieves a book instance with specific ID
   * @param id Long
   * @return BookInstance
   */
  BookInstance getBookInstanceById(Long id);

  /**
   * Retrieves a list of all book instances
   * @return List<BookInstance>
   */
  List<BookInstance> getAllBookInstances();
}
