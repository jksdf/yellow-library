package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;

/**
 * @author Norbert Slivka
 */
public interface DepartmentFacade {
  /**
   * Finds the Department specified by the ID.
   */
  DepartmentDTO findById(long id);

  /**
   * Creates new department.
   */
  void create(DepartmentDTO department);

  /**
   * Finds the Department specified by the short name.
   */
  DepartmentDTO findByShortName(String name);

  /**
   * Lists all departments.
   */
  List<DepartmentDTO> getAll();
}
