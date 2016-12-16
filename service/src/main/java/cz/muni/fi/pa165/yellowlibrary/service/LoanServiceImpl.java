package cz.muni.fi.pa165.yellowlibrary.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.LoanDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;

/**
 * @author cokinova
 */
@Service
public class LoanServiceImpl implements LoanService{

  private Logger logger = Logger.getLogger(LoanServiceImpl.class);

  @Inject
  private LoanDao loanDao;

  @Override
  public Long create(Loan loan) {
    loanDao.create(loan);
    return loan.getId();
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
  public List<Loan> getAllLoans() {
    return loanDao.findAll();
  }

  @Override
  public List<Loan> getLoansByUser(User user) {
    return loanDao.findByUser(user);
  }

  @Override
  public List<Loan> getAllLoansByBookInstance(BookInstance bookInstance) {
    return loanDao.findByBookInstance(bookInstance);
  }

  @Override
  public List<Loan> getNotReturnedLoans() {
    logger.debug("getting not returned loans");
    return loanDao.findNotReturned();
  }

  @Override
  public List<Loan> getLoansByDate(Date fromDate, Date toDate) {
    return loanDao.findByDate(fromDate, toDate);
  }

  @Override
  public void calculateFines(Calendar now) {
    List<Loan> notReturned = loanDao.findNotReturned();
    for (Loan l : notReturned) {
      Calendar loanDate = Calendar.getInstance();
      loanDate.setTime(l.getDateFrom());
      loanDate.add(Calendar.DAY_OF_YEAR, l.getLoanLength());
      if (loanDate.before(now)) {
        l.setFine(BigDecimal.valueOf(100L));
        loanDao.update(l);
      }
    }
  }
}
