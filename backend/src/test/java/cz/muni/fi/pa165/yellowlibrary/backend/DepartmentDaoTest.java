package cz.muni.fi.pa165.yellowlibrary.backend;

import com.google.common.collect.ImmutableList;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.DepartmentDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;

import static com.google.common.truth.Truth.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * TODO(slivka): not final coverage.
 * @author Norbert Slivka
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class DepartmentDaoTest extends AbstractTestNGSpringContextTests {

  @Inject
  private DepartmentDao departmentDao;

  @PersistenceContext
  private EntityManager em;

  @BeforeMethod
  public void setUp() {
  }


  @AfterMethod
  private void tearDown() {
    em.clear();
    for (Department department : ImmutableList.copyOf(em.createQuery("SELECT d FROM Department d", Department.class).getResultList())) {
      em.remove(department);
    }
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testCreateNullDepartment() {
    departmentDao.create(null);
  }

  @Test
  public void createDepartment() {
    Department dep = getDefaultDepartment();
    departmentDao.create(dep);
    Department returned = em.find(Department.class, dep.getId());
    assertDeepEquals(dep, returned);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createDepartmentNoShortName() {
    Department dep = getDefaultDepartment();
    dep.setShortName(null);
    departmentDao.create(dep);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createDepartmentNoName() {
    Department dep = getDefaultDepartment();
    dep.setName(null);
    departmentDao.create(dep);
  }

  @Test
  public void listAllDepartments() {
    Department dep1 = getDefaultDepartment();
    Department dep2 = getDefaultDepartment();
    dep2.setName("Art");
    dep2.setShortName("ART");
    em.persist(dep1);
    em.persist(dep2);
    Department expected1 = getDefaultDepartment();
    Department expected2 = getDefaultDepartment();
    expected2.setName("Art");
    expected2.setName("ART");
    List<Department> list = departmentDao.getAllDepartments();
    assertThat(list).hasSize(2);
    List<Department> dep1Returned =
        list.stream().filter(d -> d.getId().equals(dep1.getId())).collect(Collectors.toList());
    assertThat(dep1Returned).hasSize(1);
    assertDeepEquals(dep1Returned.get(0), dep1);
    List<Department> dep2Returned =
        list.stream().filter(d -> d.getId().equals(dep2.getId())).collect(Collectors.toList());
    assertThat(dep2Returned).hasSize(1);
    assertDeepEquals(dep2Returned.get(0), dep2);
  }

  @Test
  public void listNoDepartments() {
    assertThat(departmentDao.getAllDepartments()).isEmpty();
  }

  @Test
  public void removeDepartment() {
    Department dep = getDefaultDepartment();
    em.persist(dep);
    Long id = dep.getId();
    departmentDao.remove(dep);
    assertThat(em.find(Department.class, id)).isNull();
  }

  private Department getDefaultDepartment() {
    Department dep = new Department();
    dep.setName("History");
    dep.setShortName("HIS");
    return dep;
  }

  private void assertDeepEquals(Department d1, Department d2) {
    assertThat(d1.getBooks()).containsExactlyElementsIn(d1.getBooks());
    assertEquals(d1.getName(), d2.getName());
    assertEquals(d1.getShortName(), d2.getShortName());
  }

}
