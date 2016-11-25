package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Norbert Slivka
 */
@Transactional
@Repository
public class DepartmentDaoImpl implements DepartmentDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Department getDepartmentFromId(long id) {
    return entityManager.find(Department.class, id);
  }

  @Override
  public Department getDepartmentFromShortName(String shortName) {
    return entityManager.createQuery("SELECT d FROM Department d WHERE d = :name", Department.class)
        .setParameter("name", shortName)
        .getSingleResult();
  }

  @Override
  public List<Department> getAllDepartments() {
    return entityManager
        .createQuery("SELECT d FROM Department d", Department.class)
        .getResultList();
  }

  @Override
  public void remove(Department department) {
    checkNotNull(department);
    entityManager.remove(department);
  }

  @Override
  public void create(Department department) {
    checkNotNull(department);
    entityManager.persist(department);
  }

  @Override
  public void update(Department department) {
    checkNotNull(department);
    checkNotNull(department.getId());
    entityManager.merge(department);
  }
}



