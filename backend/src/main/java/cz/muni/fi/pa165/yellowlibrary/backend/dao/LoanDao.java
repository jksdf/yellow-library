package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;

/**
 * DAO for entity Loan
 *
 * @author Cokinova
 */
public interface LoanDao {
  /**
   * Persist new Loan into Database.
   *
   * @param loan Loan entity which is persisted.
   * @throws NullPointerException when input parameter is null
   * @throws IllegalArgumentException if Loan is not an entity or is a removed entity
   * @throws javax.validation.ConstraintViolationException if Loan has not well defined parameters
   */
  void create(Loan loan);

  /**
   * Deletes specified Loan from database.
   *
   * @param loan Loan to be deleted.
   * @throws NullPointerException when input parameter is null
   * @throws IllegalArgumentException if Loan is not an entity or is a removed entity
   * @throws javax.validation.ConstraintViolationException if Loan has not well defined parameters
   */
  void delete(Loan loan);

  /**
   * Finds Loan according to its id.
   *
   * @param id Unique identifier for Loan entities.
   * @throws NullPointerException when input parameter is null
   * @return Loan with specific id.
   */
  Loan findLoanById(Long id);

  /**
   * Updates persisted loan with new loan.
   *
   * @param loan New Loan entity
   * @throws NullPointerException when input parameter is null
   * @throws IllegalArgumentException if Loan is not an entity or is a removed entity
   * @throws javax.validation.ConstraintViolationException if Loan has not well defined parameters
   * @return updated Loan, if no such Loan exists return new added loan
   */
  Loan update(Loan loan);

  /**
   * Find loan with specific book instance
   * @param bookInstance
   * @return list of loans containing specific book
   */
  List<Loan> findByBookInstance(BookInstance bookInstance);

  /**
   * Find all loans
   * @return history of all loans
   */
  List<Loan> findAll();

  /**
   * Find loans which haven't been returned yet
   * @return list of loans with null returner date
   */
  List<Loan> findNotReturned();

  /**
   * Find loan created between specific date
   * @param from
   * @param to
   * @return list of loan created between specific date
   */
  List<Loan> findByDate(Date from, Date to);
}
