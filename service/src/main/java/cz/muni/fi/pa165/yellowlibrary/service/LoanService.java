package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;

/**
 * @author cokinova
 */
@Service
public interface LoanService {
  /**
   * Creates new loan according to the specified values in the parameter.
   */
  Long create(Loan loan);

  /**
   * Updates the loan specified by the ID field in the parameter to updated values.
   */
  Loan update(Loan loan);

  /**
   * Finds the loan specified by the ID. If the loan with the ID is not found,
   * throws {@link NullPointerException}.
   */
  Loan findById(Long id);

  /**
   * Retrieves the loan of the book instance, or returns {@code null}
   * if the book instance is not borrowd.
   */
  Loan currentLoanOfBookInstance(BookInstance bookInstance);

  /**
   * Retrieves all loans.
   */
  List<Loan> getAllLoans();

  /**
   * Retrieves all loans of the specified user.
   */
  List<Loan> getLoansByUser(User user);

  /**
   * Retrieves all loans of the specified book instance.
   */
  List<Loan> getAllLoansByBookInstance(BookInstance bookInstance); //loan history

  /**
   * Retrieves all loans that are not returned.
   */
  List<Loan> getNotReturnedLoans();

  /**
   * Retrieves all loans from the specified date range.
   */
  List<Loan> getLoansByDate(Date fromDate, Date expectedReturnDate);

  /**
   * Recalculates fines of all loans and updates appropriate user fine values.
   */
  void calculateFines();
}
