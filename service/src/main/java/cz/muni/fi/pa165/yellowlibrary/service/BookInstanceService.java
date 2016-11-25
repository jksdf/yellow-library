package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;

/**
 * @author Matej Gallo
 */

@Service
public interface BookInstanceService {

  BookInstance addBookInstance(BookInstance bookInstance);
  void deleteBookInstance(BookInstance bookInstance);
  void setBook(BookInstance bookInstance, Book book);
  void changeState(BookInstance bookInstance, String newState);
  void changeAvailability(BookInstance bookInstance, BookAvailability newAvailability);
  BookInstance getBookInstanceById(Long id);
  List<BookInstance> getAllBookInstances();
}
