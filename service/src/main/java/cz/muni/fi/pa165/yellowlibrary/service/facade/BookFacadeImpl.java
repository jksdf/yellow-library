package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookSearchDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.BookService;
import cz.muni.fi.pa165.yellowlibrary.service.DepartmentService;

/**
 * @author Norbert Slivka
 */
@Service
@Transactional
public class BookFacadeImpl implements BookFacade {

  @Inject
  private BookService bookService;

  @Inject
  private DepartmentService departmentService;

  @Inject
  private BeanMappingService mappingService;

  @Override
  public BookDTO getBook(long id) {
    return mappingService.mapTo(bookService.getBook(id), BookDTO.class);
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
  public long createBook(BookCreateDTO book) {
    Book created = mappingService.mapTo(book, Book.class);
    created.setDepartment(departmentService.findById(book.getDepartmentId()));
    bookService.addBook(created);
    return created.getId();
  }

  @Override
  public void updateBook(BookCreateDTO book) {
    Book updated = mappingService.mapTo(book, Book.class);
    updated.setDepartment(departmentService.findById(book.getDepartmentId()));
    bookService.editBook(updated);
  }

  @Override
  public List<BookDTO> getAll() {
    return mappingService.mapTo(bookService.getAllBooks(), BookDTO.class);
  }

}
