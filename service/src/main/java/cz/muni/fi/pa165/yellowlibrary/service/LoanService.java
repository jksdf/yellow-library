package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;

/**
 * @author cokinova
 */
@Service
public interface LoanService {
  void create(Loan loan);

  Loan update(Loan loan);

  Loan findById(Long id);

  Loan currentLoanOfBookInstance(BookInstance bookInstance);

  List<Loan> getAllLoansByBookInstance(BookInstance bookInstance); //loan history

  List<Loan> getNotReturnedLoans();

  List<Loan> getLoansByDate(Date fromDate, Date expectedReturnDate);
}
