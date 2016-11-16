package cz.muni.pa165.yellowlibrary.service;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

/**
 * @author Norbert Slivka
 */
public interface BookService {
  void addBook(Book book);

  List<Book> getAllBooks();

  List<Book> getBooksByName(String name);

  void editBook(Book book);
}
