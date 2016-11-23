package cz.muni.fi.yellowlibrary.pa165.service.facade;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;

import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;
import cz.muni.fi.yellowlibrary.pa165.service.UserServiceImpl;
import cz.muni.fi.yellowlibrary.pa165.service.configuration.ServiceConfiguration;

/**
 * @author Jozef Zivcic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest {

  @Mock
  private UserServiceImpl userService;

  @InjectMocks
  private UserFacade userFacade = new UserFacadeImpl();

}