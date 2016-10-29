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
    em.persist(loan);
  }

  @Override
  public void delete(Loan loan) {
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
    return em.merge(loan);
  }
}
