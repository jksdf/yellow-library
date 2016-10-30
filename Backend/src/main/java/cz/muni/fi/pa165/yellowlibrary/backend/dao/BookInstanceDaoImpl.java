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
    if(id == null) {
      throw new NullPointerException("ID cannot be null.");
    }
    return entityManager.find(BookInstance.class, id);
  }

  @Override
  public void deleteBookInstance(BookInstance bookInstance) {
    if(bookInstance == null) {
      throw new NullPointerException("BookInstance cannot be null.");
    }
    // TODO: Check what happens when no such entity exists
    bookInstance = entityManager.find(BookInstance.class, bookInstance.getId());
    entityManager.remove(bookInstance);
  }

  @Override
  public void createBookInstance(BookInstance bookInstance) {
    if(bookInstance == null) {
      throw new NullPointerException("BookInstance cannot be null.");
    }

    entityManager.persist(bookInstance);
  }

  @Override
  public void updateBookInstance(BookInstance bookInstance) {
    if(bookInstance == null) {
      throw new NullPointerException("BookInstance cannot be null.");
    }

    // TODO: Cannot use findById() first -- will overwrite new entity
    // entityManager.findById(BookInstance.class, bookInstance.getId());
    entityManager.merge(bookInstance);
  }
}
