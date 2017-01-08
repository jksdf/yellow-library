package cz.muni.fi.pa165.yellowlibrary.service;

import com.google.common.collect.ImmutableList;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.DepartmentDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Norbert Slivka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class DepartmentServiceTest extends AbstractTestNGSpringContextTests {
  @Mock
  private DepartmentDao departmentDao;

  @Inject
  @InjectMocks
  private DepartmentService departmentService;

  private Department department1, department2;

  @BeforeTest
  public void setUpVals() {
    department1 = new Department();
    department1.setId(1L);
    department1.setShortName("DEP");
    department1.setName("Department");

    department2 = new Department();
    department2.setId(2L);
    department2.setShortName("PED");
    department2.setName("Blaaa");
  }

  @BeforeMethod
  public void setUpMocks() {
    MockitoAnnotations.initMocks(this);
    when(departmentDao.getDepartmentFromId(1)).thenReturn(department1);
    when(departmentDao.getDepartmentFromShortName("PED")).thenReturn(department2);
    when(departmentDao.getDepartmentFromShortName(null)).thenThrow(new NullPointerException());
    when(departmentDao.getAllDepartments()).thenReturn(ImmutableList.of(department1, department2));
    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null) {
        throw new NullPointerException("Argument cannot be null");
      }
      Department department = (Department) arg;
      if (department.getId() != null) {
        throw new IllegalArgumentException("Department id must be null");
      }
      department.setId(89L);
      return department;
    }).when(departmentDao).create(any(Department.class));
  }

  @Test
  public void testCreate() {
    Department department = new Department();
    department.setShortName("PAD");
    department.setName("BlaaaaaBla");
    departmentService.create(department);
    assertThat(department.getId()).isNotNull();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testCreateNull() {
    departmentService.create(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testCreateSetId() {
    Department department = new Department();
    department.setId(50L);
    department.setShortName("PAD");
    department.setName("BlaaaaaBla");
    departmentService.create(department);
  }

  @Test
  public void testFindById() {
    Department department = departmentService.findById(department1.getId());
    assertDeepEquals(department1, department);
  }

  @Test
  public void testFindByShortName() {
    Department department = departmentService.findByShortName(department2.getShortName());
    assertDeepEquals(department2, department);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testFindByShortNameNull() {
    departmentService.findByShortName(null);
  }

  @Test
  public void testGetAll() {
    assertThat(departmentService.getAll()).containsExactly(department1, department2);
  }

  private void assertDeepEquals(Department d1, Department d2) {
    assertThat(d1.getId()).isEqualTo(d2.getId());
    assertThat(d1.getName()).isEqualTo(d2.getName());
    assertThat(d1.getShortName()).isEqualTo(d2.getShortName());
  }
}
