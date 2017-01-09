package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.DepartmentFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.DepartmentService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jozef Zivcic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class DepartmentFacadeTest extends AbstractTestNGSpringContextTests {
  @Mock
  private DepartmentService departmentService;

  @Spy
  @Inject
  private BeanMappingService mappingService;

  @InjectMocks
  private DepartmentFacade departmentFacade = new DepartmentFacadeImpl();

  private Department department;

  @BeforeMethod
  public void setUp() {
    department = new Department();
    department.setId(1L);
    department.setShortName("DEP");
    department.setName("Department");

    MockitoAnnotations.initMocks(this);
    when(departmentService.findById(1)).thenReturn(department);
    when(departmentService.findByShortName("DEP")).thenReturn(department);
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
    }).when(departmentService).create(any(Department.class));
  }

  @Test
  public void create() {
    DepartmentDTO departmentDTO = mappingService.mapTo(department, DepartmentDTO.class);
    departmentDTO.setId(null);
    departmentFacade.create(departmentDTO);
    assertThat(departmentDTO.getId()).isNotNull();
  }

  @Test
  public void byId() {
    assertDeepEquals(departmentFacade.findById(department.getId()),
        mappingService.mapTo(department, DepartmentDTO.class));
  }

  @Test
  public void byName() {
    assertDeepEquals(departmentFacade.findByShortName(department.getShortName()),
        mappingService.mapTo(department, DepartmentDTO.class));
  }

  @Test
  public void getAll() {
    departmentFacade.getAll();
    verify(departmentService).getAll();
  }

  private void assertDeepEquals(DepartmentDTO d1, DepartmentDTO d2) {
    assertThat(d1.getId()).isEqualTo(d2.getId());
    assertThat(d1.getName()).isEqualTo(d2.getName());
    assertThat(d1.getShortName()).isEqualTo(d2.getShortName());
  }
}
