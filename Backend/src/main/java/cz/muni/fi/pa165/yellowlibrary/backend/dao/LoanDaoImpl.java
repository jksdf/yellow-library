package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
      throw new NullPointerException("loan cannot be null");
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
}
