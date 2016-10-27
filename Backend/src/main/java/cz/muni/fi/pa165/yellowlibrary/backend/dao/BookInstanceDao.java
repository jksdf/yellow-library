package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;

/**
 * Created by reyvateil on 26.10.2016.
 * @author Matej Gallo
 */
public interface BookInstanceDao {
  BookInstance findById(Long id);

  void deleteBookInstance(BookInstance bookInstance);

  void createBookInstance(BookInstance bookInstance);

  void updateBookInstance(BookInstance bookInstance);
}
