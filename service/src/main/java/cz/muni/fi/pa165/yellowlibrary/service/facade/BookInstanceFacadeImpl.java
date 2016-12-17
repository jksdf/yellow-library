package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.BookInstanceService;
import cz.muni.fi.pa165.yellowlibrary.service.BookService;

/**
 * @author Matej Gallo
 */

@Service
@Transactional
public class BookInstanceFacadeImpl implements BookInstanceFacade {

  @Inject
  private BookInstanceService bookInstanceService;

  @Inject
  private BookService bookService;

  @Inject
  private BeanMappingService beanMappingService;

  @Override
  public BookInstanceDTO findById(long id) {
    BookInstance bookInstance = bookInstanceService.getBookInstanceById(id);
    if (bookInstance == null) {
      return null;
    } else {
      return beanMappingService.mapTo(bookInstance, BookInstanceDTO.class);
    }
  }

  @Override
  public Long createBookInstance(BookInstanceCreateDTO bookInstance) {
    BookInstance mappedBookInstance = beanMappingService.mapTo(bookInstance, BookInstance.class);
    // add correct Book
    mappedBookInstance.setBook(bookService.getBook(bookInstance.getBookId()));
    BookInstance newBookInstance = bookInstanceService.addBookInstance(mappedBookInstance);
    return newBookInstance.getId();
  }

  @Override
  public void changeBookState(BookInstanceNewStateDTO newStateDTO) {
    bookInstanceService.changeState(bookInstanceService.getBookInstanceById(newStateDTO.getId()),
        newStateDTO.getBookState());
  }

  @Override
  public void changeBookAvailability(Long bookInstanceId, BookInstanceAvailability bAvailability) {
    bookInstanceService.changeAvailability(bookInstanceService.getBookInstanceById(bookInstanceId),
        beanMappingService.mapTo(bAvailability, BookAvailability.class));
  }

  @Override
  public void setBook(Long bookInstanceId, Long bookId) {
    bookInstanceService.setBook(bookInstanceService.getBookInstanceById(bookInstanceId),
        bookService.getBook(bookId));
  }

  @Override
  public List<BookInstanceDTO> getAllBookInstances() {
    return beanMappingService.mapTo(bookInstanceService.getAllBookInstances(), BookInstanceDTO.class);
  }

  @Override
  public List<BookInstanceDTO> getAllBookInstancesByAvailability(BookInstanceAvailability bAvailability) {
    List<BookInstanceDTO> allBookInstances =  beanMappingService.mapTo(
        bookInstanceService.getAllBookInstances(),
        BookInstanceDTO.class);
    return allBookInstances.stream()
        .filter(bi -> bi.getBookAvailability() == bAvailability)
        .collect(Collectors.toList());
  }

  @Override
  public List<BookInstanceDTO> getAllCopies(Long bookId) {
    Book book = bookService.getBook(bookId);
    return beanMappingService.mapTo(book.getBookInstances(), BookInstanceDTO.class);
  }

  @Override
  public void deleteBookInstance(Long bookInstanceId) {
    bookInstanceService.deleteBookInstance(bookInstanceService.getBookInstanceById(bookInstanceId));
  }

  @Override
  public List<BookInstanceDTO> getAllCopiesByAvailability(Long bookId, BookInstanceAvailability bAvailability) {
    List<BookInstanceDTO> result = getAllCopies(bookId);
    result.retainAll(getAllBookInstancesByAvailability(bAvailability));
    return result;
  }

}
