package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookSearchDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UpdateBookDTO;

/**
 * @author Norbert Slivka
 */
public interface BookFacade {
  /**
   * Retrieves a book according to the ID.
   */
  BookDTO getBook(long id);

  /**
   * Finds all books according to the query defined by the parameter.
   */
  List<BookDTO> findBooks(BookSearchDTO search);

  /**
   * Creates a book according to the parameter.
   */
  long createBook(UpdateBookDTO book);

  /**
   * Updates the book specified by the ID to values in the book parameter.
   */
  void updateBook(long id, UpdateBookDTO book);
}
