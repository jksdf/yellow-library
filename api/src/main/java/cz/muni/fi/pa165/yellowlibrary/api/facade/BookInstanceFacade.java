package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;

/**
 * @author Matej Gallo
 */

public interface BookInstanceFacade {

  BookInstanceDTO findById(long id);
  Long createBookInstance(BookInstanceCreateDTO bookInstance);
  void changeBookState(Long bookInstanceId, String newState);
  void changeBookAvailability(Long bookInstanceId, BookInstanceAvailability newAvailability);
  void setBook(Long bookInstanceId, Long bookId);
  List<BookInstanceDTO> getAllBookInstances();
  List<BookInstanceDTO> getAllBorrowedBookInstances();
  List<BookInstanceDTO> getAllCopies(Long bookId);
}
