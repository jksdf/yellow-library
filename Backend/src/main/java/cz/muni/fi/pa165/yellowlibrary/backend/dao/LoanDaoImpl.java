package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;

/**
 * @author Cokinova
 */
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
    em.remove(loan);
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
