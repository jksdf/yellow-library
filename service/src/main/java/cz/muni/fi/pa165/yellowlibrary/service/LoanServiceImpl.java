package cz.muni.fi.pa165.yellowlibrary.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.exceptions.BookInstanceNotAvailableException;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookInstanceDao;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.LoanDao;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.UserDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;

/**
 * @author cokinova
 */
@Service
public class LoanServiceImpl implements LoanService {

  private Logger logger = Logger.getLogger(LoanServiceImpl.class);

  @Inject
  private LoanDao loanDao;

  @Inject
  private UserDao userDao;

  @Inject
  private DateService dateService;

  @Inject
  private BookInstanceDao bookInstanceDao;

  @Override
  public Long create(Loan loan) {
    BookInstance bookInstance = (loan == null) ? null : loan.getBookInstance();
    if (bookInstance != null && bookInstance.getBookAvailability() != BookAvailability.AVAILABLE) {
      throw new BookInstanceNotAvailableException(bookInstance.getBookAvailability().toString());
    }

    loan.getBookInstance().setBookAvailability(BookAvailability.BORROWED);
    loanDao.create(loan);
    return loan.getId();
  }

  @Override
  public Loan update(Loan loan) {
    if (loan.getReturnDate() != null) {
      loan.getBookInstance().setBookAvailability(BookAvailability.AVAILABLE);
      bookInstanceDao.updateBookInstance(loan.getBookInstance());
    }
    return loanDao.update(loan);
  }

  @Override
  public Loan findById(Long id) {
    return loanDao.findLoanById(id);
  }

  @Override
  public Loan currentLoanOfBookInstance(BookInstance bookInstance) {
    List<Loan> loans = loanDao.findByBookInstance(bookInstance);
    if (loans == null) {
      return null;
    }
    for (Loan l : loans) {
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
  @Scheduled(cron = "0 0 0 * * *")
  public void calculateFines() {
    logger.debug("Recalculating fines");
    List<Loan> notReturned = loanDao.findNotReturned();
    Calendar now = dateService.now();
    LocalDate nowLocalDate = new java.sql.Date(now.getTimeInMillis()).toLocalDate();
    for (Loan l : notReturned) {
      LocalDate lDate = Instant
          .ofEpochMilli(l.getDateFrom().getTime())
          .atZone(now.getTimeZone().toZoneId())
          .toLocalDate();

      long days = ChronoUnit.DAYS.between(lDate, nowLocalDate);
      BigDecimal fine = BigDecimal.valueOf(100L);

      if (days > l.getLoanLength() && (l.getFine() == null || l.getFine().compareTo(fine) < 0)) {
        User u = l.getUser();
        if (u.getTotalFines() == null) {
          u.setTotalFines(fine);
        }
        else {
          u.setTotalFines(u.getTotalFines().add(fine));
        }
        userDao.updateUser(u);
        l.setFine(fine);
        loanDao.update(l);

      }
    }
  }
}
