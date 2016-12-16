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

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;
import cz.muni.fi.pa165.yellowlibrary.rest.ApiUris;
import cz.muni.fi.pa165.yellowlibrary.rest.assemblers.UserAssembler;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceNotFoundException;

/**
 * @author Jozef Zivcic
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USER)
public class UserController {

  private final static Logger logger = Logger.getLogger(UserController.class);

  @Inject
  private UserFacade userFacade;

  @Inject
  private UserAssembler userAssembler;

  /**
   * Get list of all users in system by entering the following command:
   * curl -i -X GET http://localhost:8080/pa165/rest/user.
   *
   * @return All users in the system.
   */
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final HttpEntity<Resources<Resource<UserDTO>>> getUsers() {
    logger.debug("Called UserController#getUsers");
    Collection<UserDTO> userDTOs = userFacade.findAllUsers();
    Collection<Resource<UserDTO>> userResourceCollection = new ArrayList<>();
    for (UserDTO u : userDTOs) {
      userResourceCollection.add(userAssembler.toResource(u));
    }
    Resources<Resource<UserDTO>> userResources = new Resources<Resource<UserDTO>>
        (userResourceCollection);
    return new ResponseEntity<Resources<Resource<UserDTO>>>(userResources, HttpStatus.OK);
  }

  /**
   * Searches for user with identification given as the parameter. Sample call:
   * curl -i -X GET http://localhost:8080/pa165/rest/user/<number> where <number> is the id of user.
   *
   * @param id Identification of user.
   * @return Found user.
   * @throws ResourceNotFoundException if user with given id is not present in the system.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public final HttpEntity<Resource<UserDTO>> getUserById(@PathVariable("id") long id) {
    logger.debug("Called UserController#getUserById");
    UserDTO userDTO = userFacade.findById(id);
    if (userDTO == null)
      throw new ResourceNotFoundException();
    Resource<UserDTO> userDTOResource = userAssembler.toResource(userDTO);
    return new ResponseEntity<Resource<UserDTO>>(userDTOResource, HttpStatus.OK);
  }

  /**
   * Creates the new user in the system. Sample call can look like:
   * curl -X POST -i -H "Content-Type: application/json" --data '{"name" : "Peter Green", "login" : "xgreen", "passwordHash" : "admin", "address" : "Ulica 3 Mesto", "totalFines" : "12.14", "userType" : "EMPLOYEE"}' http://localhost:8080/pa165/rest/user/create
   * @param userDTO User which is created in the system.
   * @return Newly created user.
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public final HttpEntity<Resource<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
    logger.debug("Called UserController#createUser");
    System.out.println("HashPaswword= " + userDTO.getPasswordHash());
    userFacade.registerNewUser(userDTO, userDTO.getPasswordHash());
    UserDTO ret = userFacade.findById(userDTO.getId());
    Resource<UserDTO> userDTOResource = userAssembler.toResource(ret);
    return new ResponseEntity<Resource<UserDTO>>(userDTOResource, HttpStatus.OK);
  }
}
