package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;

/**
 * @author cokinova
 */
public interface LoanFacade {
  /**
   * Creates new loan according to the specified values in the parameter.
   */
  Long create(LoanCreateDTO loan);

  /**
   * Updates the loan specified by the ID field in the parameter to updated values.
   */
  LoanDTO update(LoanDTO loan);

  /**
   * Finds the loan specified by the ID. If the loan with the ID is not found,
   * throws {@link NullPointerException}.
   */
  LoanDTO findById(Long id);

  /**
   * Retieves all loans.
   */
  List<LoanDTO> getAllLoans();

  /**
   * Retrieves all loans of the specified user.
   */
  List<LoanDTO> getLoansByUser(UserDTO user);

  /**
   * Retrieves the loan of the book instance, or returns {@code null}
   * if the book instance is not borrowd.
   */
  LoanDTO currentLoanOfBookInstance(BookInstanceDTO bookInstance);

  /**
   * Retrieves all loans of the specified book instance.
   */
  List<LoanDTO> getAllBookInstanceLoans(BookInstanceDTO bookInstance);

  /**
   * Retrieves all loans that are not returned.
   */
  List<LoanDTO> getNotReturnedLoans();

  /**
   * Retrieves all loans from the specified date range.
   */
  List<LoanDTO> getLoansByDate(Date fromDate, Date expectedReturnDate);

  /**
   * Recalculates fines of all loans.
   */
  void calculateFinesForExpiredLoans();
}
