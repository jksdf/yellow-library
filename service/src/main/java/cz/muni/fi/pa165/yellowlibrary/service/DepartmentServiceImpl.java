package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.DepartmentDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;

/**
 * @author Norbert Slivka
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

  @Inject
  private DepartmentDao departmentDao;

  @Override
  public Department findById(long id) {
    return departmentDao.getDepartmentFromId(id);
  }

  @Override
  public void create(Department department) {
    departmentDao.create(department);
  }

  @Override
  public Department findByShortName(String name) {
    return departmentDao.getDepartmentFromShortName(name);
  }

  @Override
  public List<Department> getAll() {
    return departmentDao.getAllDepartments();
  }
}
