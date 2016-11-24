package cz.muni.fi.pa165.yellowlibrary.service.facade;

import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;

/**
 * @author Norbert Slivka
 */
public class LoanFacadeImpl implements LoanFacade {
  @Override
  public void create(LoanDTO loan) {

  }

  @Override
  public LoanDTO update(LoanDTO loan) {
    return null;
  }

  @Override
  public LoanDTO findById(Long id) {
    return null;
  }

  @Override
  public LoanDTO currentLoanOfBookInstance(BookInstanceDTO bookInstance) {
    return null;
  }

  @Override
  public List<LoanDTO> getAllBookInstanceLoans(BookInstanceDTO bookInstance) {
    return null;
  }

  @Override
  public List<LoanDTO> getNotReturnedLoans() {
    return null;
  }

  @Override
  public List<LoanDTO> getLoansByDate(Date fromDate, Date expectedReturnDate) {
    return null;
  }

  @Override
  public void CalculateFinesForExpiredLoans() {

  }
}
