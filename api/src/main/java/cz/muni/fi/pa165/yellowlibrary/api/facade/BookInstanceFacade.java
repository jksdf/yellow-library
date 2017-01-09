package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;

/**
 * @author Matej Gallo
 */

public interface BookInstanceFacade {

  /**
   * Retrieves book instance by ID
   * @param id Long
   * @return BookInstanceDTO
   */
  BookInstanceDTO findById(long id);

  /**
   * Creates a new book instance
   * @param bookInstance BookInstanceCreateDTO
   * @return Long ID of the book instance
   */
  Long createBookInstance(BookInstanceCreateDTO bookInstance);

  /**
   * Changes the state of book instance
   * @param newStateDTO BookInstanceNewStateDTO
   */
  void changeBookState(BookInstanceNewStateDTO newStateDTO);

  /**
   * Changes the availability of book instance
   * @param bookInstanceId Long
   * @param bAvailability BookInstanceAvailability
   */
  void changeBookAvailability(Long bookInstanceId, BookInstanceAvailability bAvailability);

  /**
   * Sets the book instance parent book
   * @param bookInstanceId Long
   * @param bookId Long
   */
  void setBook(Long bookInstanceId, Long bookId);

  /**
   * Deletes a book instance
   * @param bookInstanceId Long
   */
  void deleteBookInstance(Long bookInstanceId);

  /**
   * Retrieves list of all book instances
   * @return List<BookInstanceDTO>
   */
  List<BookInstanceDTO> getAllBookInstances();

  /**
   * Retrieves list of all book instances filtered by the availability
   * @param bAvailability BookInstanceAvailability
   * @return List<BookInstanceDTO>
   */
  List<BookInstanceDTO> getAllBookInstancesByAvailability(BookInstanceAvailability bAvailability);

  /**
   * Retrieves all book instances of the same book
   * @param bookId Long
   * @return List<BookInstanceDTO>
   */
  List<BookInstanceDTO> getAllCopies(Long bookId);

  /**
   * Retrieves all book instances of the same book filtered by availability
   * @param bookId Long
   * @param bAvailability BookInstanceAvailability
   * @return List<BookInstanceDTO>
   */
  List<BookInstanceDTO> getAllCopiesByAvailability(Long bookId, BookInstanceAvailability bAvailability);
}
