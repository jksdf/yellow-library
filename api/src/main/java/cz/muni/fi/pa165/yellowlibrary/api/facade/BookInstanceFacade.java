package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewAvailabilityDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;

/**
 * @author Matej Gallo
 */

public interface BookInstanceFacade {

  BookInstanceDTO findById(long id);
  Long createBookInstance(BookInstanceCreateDTO bookInstance);
  void changeBookState(BookInstanceNewStateDTO newStateDTO);
  void changeBookAvailability(BookInstanceNewAvailabilityDTO newAvailabilityDTO);
  void setBook(Long bookInstanceId, Long bookId);
  void deleteBookInstance(Long bookInstanceId);
  List<BookInstanceDTO> getAllBookInstances();
  List<BookInstanceDTO> getAllBookInstancesByAvailability(BookInstanceAvailability bAvailability);
  List<BookInstanceDTO> getAllCopies(Long bookId);
}
