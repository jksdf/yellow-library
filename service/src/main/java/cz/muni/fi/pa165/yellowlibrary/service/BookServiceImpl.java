package cz.muni.fi.pa165.yellowlibrary.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

/**
 * @author Norbert Slivka
 */
public class BookServiceImpl implements BookService {

  @Inject
  private BookDao bookDao;

  @Override
  public void addBook(Book book) {
    bookDao.create(book);
  }

  @Override
  public Book getBook(long id) {
    return bookDao.getBookFromId(id);
  }

  @Override
  public List<Book> getAllBooks() {
    return bookDao.getAllBooks();
  }

  @Override
  public List<Book> searchBooks(String author, String name, String description, String isbn,
                                Set<Long> departments) {
    // TODO(slivka): Implement this in DAO.
    return bookDao.getAllBooks()
        .stream()
        .filter(book ->
            (author != null && book.getAuthor().contains(author)) ||
                (name != null && book.getName().contains(name)) ||
                (description != null && book.getDescription().contains(description)) ||
                (departments != null && departments.contains(book.getDepartment().getId())))
        .collect(Collectors.toList());
  }

  @Override
  public void editBook(Book book) {
    bookDao.update(book);
  }
}
