package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.DepartmentFacade;
import cz.muni.fi.pa165.yellowlibrary.rest.ApiUris;

/**
 * @author Norbert Slivka
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_DEPARTMENT)
public class DepartmentController {

  private final static Logger logger = Logger.getLogger(DepartmentController.class);

  @Inject
  private DepartmentFacade departmentFacade;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final List<DepartmentDTO> getDepartments() {

    logger.debug("rest getDepartments()");
    return departmentFacade.getAll();
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public final DepartmentDTO addDepartment(@RequestBody DepartmentDTO department) {
    departmentFacade.create(department);
    return department;
  }
}
