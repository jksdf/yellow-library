package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.DateService;
import cz.muni.fi.pa165.yellowlibrary.service.LoanService;

/**
 * @author cokinova
 */
@Service
@Transactional
public class LoanFacadeImpl implements LoanFacade {
  @Inject
  LoanService loanService;

  @Inject
  private BeanMappingService mappingService;

  @Inject
  private DateService dateService;

  @Override
  public void create(LoanDTO loanDTO) {
    if (loanDTO == null) {
      throw new NullPointerException("LoanDTO cannot be null");
    }
    Loan loan = mappingService.mapTo(loanDTO, Loan.class);
    loanService.create(loan);
    loanDTO.setId(loan.getId());
  }

  @Override
  public LoanDTO update(LoanDTO loanDTO) {
    if (loanDTO == null) {
      throw new NullPointerException("LoanDTO cannot be null");
    }
    Loan loan = mappingService.mapTo(loanDTO, Loan.class);
    Loan l = loanService.update(loan);
    return mappingService.mapTo(l, LoanDTO.class);
  }

  @Override
  public LoanDTO findById(Long id) {
    if (id == null) {
      throw new NullPointerException("id cannot be null");
    }
    Loan loan = loanService.findById(id);
    return mappingService.mapTo(loan, LoanDTO.class);
  }

  @Override
  public LoanDTO currentLoanOfBookInstance(BookInstanceDTO bookInstanceDTO) {
    if (bookInstanceDTO == null) {
      throw new NullPointerException("bookInstanceDTO cannot be null");
    }
    BookInstance bookInstance = mappingService.mapTo(bookInstanceDTO, BookInstance.class);
    Loan loan = loanService.currentLoanOfBookInstance(bookInstance);
    return mappingService.mapTo(loan, LoanDTO.class);
  }

  @Override
  public List<LoanDTO> getAllBookInstanceLoans(BookInstanceDTO bookInstanceDTO) {
    if (bookInstanceDTO == null) {
      throw new NullPointerException("bookInstanceDTO cannot be null");
    }
    BookInstance bookInstance = mappingService.mapTo(bookInstanceDTO, BookInstance.class);
    List<Loan> loans = loanService.getAllLoansByBookInstance(bookInstance);
    return mappingService.mapTo(loans, LoanDTO.class);
  }

  @Override
  public List<LoanDTO> getNotReturnedLoans() {
    List<Loan> loans = loanService.getNotReturnedLoans();
    return mappingService.mapTo(loans, LoanDTO.class);
  }

  @Override
  public List<LoanDTO> getLoansByDate(Date fromDate, Date expectedReturnDate) {
    if (fromDate == null) {
      throw new NullPointerException("fromDate cannot be null");
    }
    if (expectedReturnDate == null) {
      throw new NullPointerException("expectedReturnDate cannot be null");
    }
    List<Loan> loans = loanService.getLoansByDate(fromDate, expectedReturnDate);
    return mappingService.mapTo(loans, LoanDTO.class);
  }

  @Override
  public void CalculateFinesForExpiredLoans() {
    loanService.calculateFines(dateService.now());
  }
}
