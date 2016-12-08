package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;

/**
 * @author Norbert Slivka
 */
@Service
public interface DepartmentService {
  /**
   * Finds the Department specified by the ID.
   */
  Department findById(long id);

  /**
   * Creates new department.
   */
  void create(Department department);

  /**
   * Finds the Department specified by the short name.
   */
  Department findByShortName(String name);

  List<Department> getAll();
}
