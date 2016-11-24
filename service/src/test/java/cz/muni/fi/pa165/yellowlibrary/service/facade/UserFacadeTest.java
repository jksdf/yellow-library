package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.UserService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.yellowlibrary.service.utils.UserUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Jozef Zivcic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

  public static final Long user1Id = 1L;
  public static final Long user2Id = 2L;
  public static final Long nonExistingId = 3L;

  @Mock
  private UserService userService;

  @Spy
  @Inject
  private BeanMappingService mappingService;

  @InjectMocks
  private UserFacade userFacade = new UserFacadeImpl();

  private User user1;
  private User user2;

  @BeforeMethod
  public void setUp() {
    setUpUsers();
    MockitoAnnotations.initMocks(this);
    when(userService.findById(user2.getId())).thenReturn(user2);
    when(userService.findByLogin(user2.getLogin())).thenReturn(user2);
    when(userService.findAllUsers()).thenReturn(Arrays.asList(user1, user2));
    when(userService.isEmployee(user1)).thenReturn(user1.isEmployee());
    when(userService.isCustomer(user1)).thenReturn(user1.isCustomer());
    when(userService.isEmployee(user2)).thenReturn(user2.isEmployee());
    when(userService.isCustomer(user2)).thenReturn(user2.isCustomer());
  }

  @Test
  public void createUserTest() {
    UserDTO userDTO = mappingService.mapTo(user1, UserDTO.class);
    userFacade.createUser(userDTO);
    verify(userService).create(any(User.class));
  }

  @Test
  public void findByIdTest() {
    UserDTO retDTO = userFacade.findById(user2Id);
    verify(userService).findById(user2Id);
    User retUser = mappingService.mapTo(retDTO, User.class);
    UserUtils.assertDeepEquals(retUser, user2);
  }

  @Test
  public void findByLoginTest() {
    UserDTO retDTO = userFacade.findByLogin(user2.getLogin());
    verify(userService).findByLogin(user2.getLogin());
    User retUser = mappingService.mapTo(retDTO, User.class);
    UserUtils.assertDeepEquals(retUser, user2);
  }

  @Test
  public void findAllUsersTest() {
    List<UserDTO> retDTO = userFacade.findAllUsers();
    verify(userService).findAllUsers();
    List<User> users = mappingService.mapTo(retDTO, User.class);
    assertEquals(users.size(), 2);
    assertTrue(users.contains(user1));
    assertTrue(users.contains(user2));
  }

  @Test
  public void isEmployeeTest() {
    UserDTO userDTO = mappingService.mapTo(user2, UserDTO.class);
    assertTrue(userFacade.isEmployee(userDTO));
    assertFalse(userFacade.isCustomer(userDTO));
  }

  @Test
  public void isCustomerTest() {
    UserDTO userDTO = mappingService.mapTo(user1, UserDTO.class);
    assertTrue(userFacade.isCustomer(userDTO));
    assertFalse(userFacade.isEmployee(userDTO));
  }

  private void setUpUsers() {
    user1 = new User();
    user1.setName("Joshua Bloch");
    user1.setAddress("USA");
    user1.setLogin("xbloch");
    user1.setUserType(UserType.CUSTOMER);
    user1.setTotalFines(BigDecimal.ZERO);

    user2 = new User();
    user2.setId(user2Id);
    user2.setName("John Good");
    user2.setAddress("4125 7th Ave, New York, NY 10022, USA");
    user2.setLogin("xgood");
    user2.setUserType(UserType.EMPLOYEE);
    user2.setTotalFines(BigDecimal.ZERO);
  }
}
