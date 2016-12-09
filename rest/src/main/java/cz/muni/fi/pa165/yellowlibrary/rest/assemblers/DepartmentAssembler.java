package cz.muni.fi.pa165.yellowlibrary.rest.assemblers;

import org.apache.log4j.Logger;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
import cz.muni.fi.pa165.yellowlibrary.rest.controllers.DepartmentController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Norbert Slivka
 */
@Component
public class DepartmentAssembler
    implements ResourceAssembler<DepartmentDTO, Resource<DepartmentDTO>> {

  private final Logger logger = Logger.getLogger(DepartmentAssembler.class);

  @Override
  public Resource<DepartmentDTO> toResource(DepartmentDTO departmentDTO) {
    Resource<DepartmentDTO> resource = new Resource<>(departmentDTO);
    try {
      resource.add(
          linkTo(DepartmentController.class).slash(departmentDTO.getId()).withSelfRel());
    } catch (Exception ex) {
      logger.error("Could not create a resource for the department", ex);
    }
    return resource;
  }
}
