package cz.muni.fi.yellowlibrary.pa165.service.facade;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookSearchDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UpdateBookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.yellowlibrary.pa165.service.BeanMappingService;
import cz.muni.fi.yellowlibrary.pa165.service.BookService;

/**
 * @author Norbert Slivka
 */
public class BookFacadeImpl implements BookFacade {

  @Inject
  private BookService bookService;

  @Inject
  private BeanMappingService mappingService;

  @Override
  public BookDTO getBook(long id) {
    return null;
  }

  @Override
  public List<BookDTO> findBooks(BookSearchDTO search) {
    List<Book> books = bookService.searchBooks(
        search.getAuthor(),
        search.getName(),
        search.getDescription(),
        search.getIsbn(),
        search.getDepartmentIds());
    return mappingService.mapTo(books, BookDTO.class);
  }

  @Override
  public long createBook(UpdateBookDTO book) {
    return 0;
  }

  @Override
  public void updateBook(long id, UpdateBookDTO book) {

  }
}
