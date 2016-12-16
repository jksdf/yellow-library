package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;

/**
 * @author cokinova
 */
public interface LoanFacade {
  Long create(LoanCreateDTO loan);

  LoanDTO update(LoanDTO loan);

  LoanDTO findById(Long id);

  LoanDTO currentLoanOfBookInstance(BookInstanceDTO bookInstance);

  List<LoanDTO> getAllBookInstanceLoans(BookInstanceDTO bookInstance); //loan history

  List<LoanDTO> getNotReturnedLoans();

  List<LoanDTO> getLoansByDate(Date fromDate, Date expectedReturnDate);

  void CalculateFinesForExpiredLoans();
}
