package cz.muni.fi.pa165.yellowlibrary.rest.assemblers;

import org.apache.log4j.Logger;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Jozef Zivcic
 */
public class UserAssembler implements ResourceAssembler<UserDTO, Resource<UserDTO>> {

  private final Logger logger = Logger.getLogger(DepartmentAssembler.class);

  @Override
  public Resource<UserDTO> toResource(UserDTO userDTO) {
    Long userId = userDTO.getId();
    Resource<UserDTO> userDTOResource = new Resource<>(userDTO);
    try {
      userDTOResource.add(linkTo(UserAssembler.class).slash(userId).withSelfRel());
    } catch (Exception ex) {
      logger.error("Could not create a resource for user", ex);
    }
    return userDTOResource;
  }
}
