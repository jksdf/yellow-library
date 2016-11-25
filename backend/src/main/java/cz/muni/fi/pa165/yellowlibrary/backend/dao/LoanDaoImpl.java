package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;

/**
 * @author Cokinova
 */
@Transactional
@Repository
public class LoanDaoImpl implements LoanDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void create(Loan loan) {
    if (loan == null) {
      throw new NullPointerException("Loan cannot be null");
    }
    em.persist(loan);
  }

  @Override
  public void delete(Loan loan) {
    if (loan == null) {
      throw new NullPointerException("loan cannot be null");
    }
    em.remove(em.find(Loan.class, loan.getId()));
  }

  @Override
  public Loan findLoanById(Long id) {
    if (id == null) {
      throw new NullPointerException("Id cannot be null");
    }
    return em.find(Loan.class, id);
  }

  @Override
  public Loan update(Loan loan) {
    if (loan == null) {
      throw new NullPointerException("loan cannot be null");
    }
    return em.merge(loan);
  }

  @Override
  public List<Loan> findByBookInstance(BookInstance bookInstance) {
    if (bookInstance == null) {
      throw new NullPointerException("bookInstance cannot be null");
    }

    return em
        .createQuery("select l from Loan l WHERE l.bookInstance = :bookInstance",
            Loan.class).setParameter("bookInstance", bookInstance).getResultList();
  }

  @Override
  public List<Loan> findAll() {
    return em.createQuery("select l from Loan l", Loan.class)
        .getResultList();
  }

  @Override
  public List<Loan> findNotReturned() {
    return em.createQuery("select l from Loan l where l.returnDate is null", Loan.class)
        .getResultList();
  }

  @Override
  public List<Loan> findByDate(Date fromDate, Date toDate) {
    if (fromDate == null) {
      throw new NullPointerException("fromDate cannot be null");
    }
    if (toDate == null) {
      throw new NullPointerException("toDate cannot be null");
    }
    if(fromDate.after(toDate)) {
      throw new IllegalArgumentException("fromDate cannot be after toDate");
    }
    return em.createQuery("select l from Loan l where l.returnDate between :fromDate AND :toDate",
        Loan.class)
        .setParameter("fromDate", fromDate, TemporalType.DATE)
        .setParameter("toDate", toDate, TemporalType.DATE)
        .getResultList();
  }
}
