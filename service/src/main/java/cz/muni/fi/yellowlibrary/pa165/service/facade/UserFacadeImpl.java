package cz.muni.fi.yellowlibrary.pa165.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;
import cz.muni.fi.yellowlibrary.pa165.service.BeanMappingService;
import cz.muni.fi.yellowlibrary.pa165.service.UserService;

/**
 * @author Jozef Zivcic
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

  @Inject
  private UserService userService;

  @Inject
  private BeanMappingService mappingService;

  @Override
  public void createUser(UserDTO userDTO) {

  }

  @Override
  public UserDTO findById(Long id) {
    return null;
  }

  @Override
  public UserDTO findByLogin(String login) {
    return null;
  }

  @Override
  public List<UserDTO> findAllUsers() {
    return null;
  }

  @Override
  public boolean isEmployee(UserDTO userDTO) {
    return false;
  }

  @Override
  public boolean isCustomer(UserDTO userDTO) {
    return false;
  }
}
