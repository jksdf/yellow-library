package cz.muni.fi.yellowlibrary.pa165.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.LoanDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;

/**
 * @author cokinova
 */
public class LoanServiceImpl implements LoanService{
  @Inject
  private LoanDao loanDao;

  @Override
  public void create(Loan loan) {
    loanDao.create(loan);
  }

  @Override
  public Loan update(Loan loan) {
    return loanDao.update(loan);
  }

  @Override
  public Loan findById(Long id) {
    return loanDao.findLoanById(id);
  }

  @Override
  public Loan currentLoanOfBookInstance(BookInstance bookInstance) {
    List<Loan> loans = loanDao.findByBookInstance(bookInstance);
    if (loans == null) {return null;}
    for(Loan l : loans) {
      if (l.getReturnDate() == null) {
        return l;
      }
    }
    return null;
  }

  @Override
  public List<Loan> getAllLoansByBookInstance(BookInstance bookInstance) {
    return loanDao.findByBookInstance(bookInstance);
  }

  @Override
  public List<Loan> getNotReturnedLoans() {
    return loanDao.findNotReturned();
  }

  @Override
  public List<Loan> getLoansByDate(Date fromDate, Date expectedReturnDate) {
    return null;
  }
}
