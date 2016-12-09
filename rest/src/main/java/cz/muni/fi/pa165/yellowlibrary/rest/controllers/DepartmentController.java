package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

import org.apache.log4j.Logger;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.DepartmentFacade;
import cz.muni.fi.pa165.yellowlibrary.rest.ApiUris;
import cz.muni.fi.pa165.yellowlibrary.rest.assemblers.DepartmentAssembler;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceNotFoundException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * TODO(slivka): caching
 * @author Norbert Slivka
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_DEPARTMENT)
public class DepartmentController {

  private final static Logger logger = Logger.getLogger(DepartmentController.class);

  @Inject
  private DepartmentFacade departmentFacade;

  @Inject
  private DepartmentAssembler departmentAssembler;

  @RequestMapping(method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public final HttpEntity<Resources<Resource<DepartmentDTO>>> getDepartments() {
    logger.debug("REST getDepartments()");

    Resources<Resource<DepartmentDTO>> resources =
        new Resources<>(departmentFacade.getAll().stream()
            .map(dep -> departmentAssembler.toResource(dep))
            .collect(Collectors.toList()));
    resources.add(linkTo(DepartmentController.class).withSelfRel());
    return new ResponseEntity<>(resources, HttpStatus.OK);
  }

  @RequestMapping(value = "/create",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public final HttpEntity<Resource<DepartmentDTO>> addDepartment(
      @RequestBody DepartmentDTO department) {
    logger.debug("REST addDepartment()");
    departmentFacade.create(department);
    return new ResponseEntity<>(departmentAssembler.toResource(department), HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public final HttpEntity<Resource<DepartmentDTO>> getById(@PathVariable("id") long id) {
    DepartmentDTO departmentDTO = departmentFacade.findById(id);
    if (departmentDTO == null) {
      throw new ResourceNotFoundException();
    }
    return new ResponseEntity<>(departmentAssembler.toResource(departmentDTO), HttpStatus.FOUND);
  }
}
