package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;

/**
 * Created by reyvateil on 26.10.2016.
 * @author Matej Gallo
 */
@Repository
public class BookInstanceDaoImpl implements BookInstanceDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public BookInstance findById(Long id) {
    return entityManager.find(BookInstance.class, id);
  }

  @Override
  public void deleteBookInstance(BookInstance bookInstance) {
    bookInstance = entityManager.merge(bookInstance);
    entityManager.remove(bookInstance);
  }

  @Override
  public void createBookInstance(BookInstance bookInstance) {
    entityManager.persist(bookInstance);
  }

  public void updateBookInstance(BookInstance bookInstance) {
    entityManager.merge(bookInstance);
  }
}
