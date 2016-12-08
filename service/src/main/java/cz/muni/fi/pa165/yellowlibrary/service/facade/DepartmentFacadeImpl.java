package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.DepartmentFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.DepartmentService;

/**
 * @author Norbert Slivka
 */
@Service
@Transactional
public class DepartmentFacadeImpl implements DepartmentFacade {

  @Inject
  private DepartmentService departmentService;

  @Inject
  private BeanMappingService mappingService;

  @Override
  public DepartmentDTO findById(long id) {
    return mappingService.mapTo(departmentService.findById(id), DepartmentDTO.class);
  }

  @Override
  public void create(DepartmentDTO department) {
    Department department1 = mappingService.mapTo(department, Department.class);
    departmentService.create(department1);
    department.setId(department1.getId());
  }

  @Override
  public DepartmentDTO findByShortName(String name) {
    return mappingService.mapTo(departmentService.findByShortName(name), DepartmentDTO.class);
  }

  @Override
  public List<DepartmentDTO> getAll() {
    return mappingService.mapTo(departmentService.getAll(), DepartmentDTO.class);
  }
}
