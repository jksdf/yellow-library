package cz.muni.fi.yellowlibrary.pa165.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.UserDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;
import cz.muni.fi.yellowlibrary.pa165.service.configuration.ServiceConfiguration;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.*;


/**
 * @author Jozef Zivcic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

  @Mock
  private UserDao userDao;

  @Inject
  @InjectMocks
  private UserService userService;

  private User user;
  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    user = new User();
    user.setName("Joshua Bloch");
    user.setAddress("USA");
    user.setLogin("xbloch");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
  }

  @Test
  public void createValidUserTest() {
    userService.create(user);
    verify(userDao).createUser(user);
    verifyNoMoreInteractions(userDao);
  }
}