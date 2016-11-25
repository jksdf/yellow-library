package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;

/**
 * @author Norbert Slivka
 */
public interface DepartmentDao {

  /**
   * Retrieve the department.
   */
  Department getDepartmentFromId(long id);

  /**
   * Retrieve the department.
   */
  Department getDepartmentFromShortName(String shortName);

  /**
   * Retrieve all department.
   */
  List<Department> getAllDepartments();

  /**
   * Remove the department.
   */
  void remove(Department book);

  /**
   * Store new department.
   */
  void create(Department book);

  /**
   * Updates the stored department (according to the id). The ID can not be {@code null}.
   */
  void update(Department book);
}
