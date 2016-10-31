package cz.muni.fi.pa165.yellowlibrary.backend.dao;


import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * @author Norbert Slivka
 */
@Repository
public class BookDaoImpl implements BookDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Book getBookFromId(long id) {
    return entityManager.find(Book.class, id);
  }

  @Override
  public List<Book> getAllBooks() {
    return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
  }

  @Override
  public void remove(Book book) {
    checkNotNull(book);
    entityManager.remove(book);
  }

  @Override
  public void create(Book book) {
    checkNotNull(book);
    entityManager.persist(book);
  }

  @Override
  public void update(Book book) {
    checkNotNull(book);
    entityManager.merge(book);
  }
}
