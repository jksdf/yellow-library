package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.exceptions.YellowServiceException;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookInstanceDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;

/**
 * @author Matej Gallo
 */

@Service
public class BookInstanceServiceImpl implements BookInstanceService {

  @Inject
  private BookInstanceDao bookInstanceDao;

  @Inject
  private LoanService loanService;

  @Override
  public BookInstance addBookInstance(BookInstance bookInstance) {
    bookInstanceDao.createBookInstance(bookInstance);
    return bookInstance;
  }

  @Override
  public void deleteBookInstance(BookInstance bookInstance) {
    if ( loanService.currentLoanOfBookInstance(bookInstance) != null ) {
      throw new YellowServiceException("Loan with this book instance currently exists.");
    }
    bookInstanceDao.deleteBookInstance(bookInstance);
  }

  @Override
  public void setBook(BookInstance bookInstance, Book book) {
    bookInstance.setBook(book);
    bookInstanceDao.updateBookInstance(bookInstance);
  }

  @Override
  public void changeState(BookInstance bookInstance, String newState) {
    bookInstance.setBookState(newState);
    bookInstanceDao.updateBookInstance(bookInstance);
  }

  @Override
  public void changeAvailability(BookInstance bookInstance, BookAvailability newAvailability) {
    bookInstance.setBookAvailability(newAvailability);
    bookInstanceDao.updateBookInstance(bookInstance);
  }

  @Override
  public BookInstance getBookInstanceById(Long id) {
    return bookInstanceDao.findById(id);
  }

  @Override
  public List<BookInstance> getAllBookInstances() {
    return bookInstanceDao.findAll();
  }
}
