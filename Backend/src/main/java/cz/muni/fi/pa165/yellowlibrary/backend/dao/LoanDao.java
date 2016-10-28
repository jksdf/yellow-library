package cz.muni.fi.pa165.yellowlibrary.backend.dao;

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
   */
  void create(Loan loan);

  /**
   * Deletes specified Loan from database.
   *
   * @param loan Loan to be deleted.
   */
  void delete(Loan loan);

  /**
   * Finds Loan according to its id.
   *
   * @param id Unique identifier for Loan entities.
   * @return Loan with specific id.
   */
  Loan findLoanById(Long id);

  /**
   * Updates persisted loan with new loan.
   *
   * @param loan New Loan entity
   */
  Loan update(Loan loan);
}
