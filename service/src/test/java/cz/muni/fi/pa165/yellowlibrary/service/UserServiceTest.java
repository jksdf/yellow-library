package cz.muni.fi.pa165.yellowlibrary.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.UserDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.yellowlibrary.service.utils.UserUtils;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;


/**
 * @author Jozef Zivcic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

  public static final Long user1Id = 10L;
  public static final Long user3Id = 1L;
  public static final Long nonExistingId = 3L;

  @Mock
  private UserDao userDao;

  @Inject
  @InjectMocks
  private UserService userService;
  private User user1;
  private User user2;

  private User user3;

  @BeforeMethod
  public void setUp() {
    setUpUsers();
    MockitoAnnotations.initMocks(this);
    when(userDao.findById(user3Id)).thenReturn(user3);
    when(userDao.findById(user1Id)).thenReturn(user1);
    when(userDao.findAllUsers()).thenReturn(Arrays.asList(user1, user2));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null)
        throw new NullPointerException("Argument cannot be null");
      User user = (User) arg;
      if (user.getId() != null)
        throw new NullPointerException("User id must be null");
      user.setId(user1Id);
      return null;
    }).when(userDao).createUser(any(User.class));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null)
        throw new NullPointerException("Argument cannot be null");
      User user = (User) arg;
      if (user.getId() == null)
        throw new NullPointerException("User id must be filled");
      return null;
    }).when(userDao).updateUser(any(User.class));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null)
        throw new NullPointerException("Argument cannot be null");
      User user = (User) arg;
      if (user.getId() == null)
        throw new NullPointerException("User id has to be filled");
      userDao.findById(user.getId());
      return null;
    }).when(userDao).deleteUser(any(User.class));

  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createNullUserTest() {
    userService.create(null);
  }

  @Test
  public void createValidUserTest() {
    userService.create(user1);
    verify(userDao).createUser(user1);
    verifyNoMoreInteractions(userDao);
    assertEquals(user1Id, user1.getId());
  }

  // update

  @Test(expectedExceptions = NullPointerException.class)
  public void updateNullUserTest() {
    userService.update(null);
  }

  @Test
  public void updateUserTest() {
    user3.setAddress("Brno");
    userService.update(user3);
    User retUser = userService.findById(user3.getId());
    UserUtils.assertDeepEquals(retUser, user3);
  }

  // delete

  @Test(expectedExceptions = NullPointerException.class)
  public void deleteNullUserTest() {
    userService.delete(null);
  }

  @Test
  public void deleteUserTest() {
    User newUser = UserUtils.copyUser(user3);
    newUser.setId(nonExistingId);
    when(userDao.findById(newUser.getId())).thenReturn(newUser).thenReturn(null);
    userService.delete(newUser);
    assertNull(userService.findById(nonExistingId));
  }

  // find

  @Test
  public void findByNonExistingIdTest() {
    User user = userService.findById(nonExistingId);
    assertNull(user);
  }

  @Test
  public void findByExistingIdsTest() {
    User user = userService.findById(user3Id);
    UserUtils.assertDeepEquals(user, user3);
  }

  @Test
  public void findAllUsersTest() {
    List<User> users = userService.findAllUsers();
    assertEquals(users.size(), 2);
  }

  // findByLogin

  @Test(expectedExceptions = NullPointerException.class)
  public void findByNullLoginTest() {
    when(userDao.findByLogin(null)).thenThrow(NullPointerException.class);
    userService.findByLogin(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void findByEmptyLoginTest() {
    when(userDao.findByLogin("")).thenThrow(IllegalArgumentException.class);
    userService.findByLogin("");
  }

  @Test
  public void findByLoginTest() {
    when(userDao.findByLogin(user3.getLogin())).thenReturn(user3);
    User retUser = userService.findByLogin(user3.getLogin());
    UserUtils.assertDeepEquals(retUser, user3);
  }

  // is employee

  @Test
  public void isEmployeeTest() {
    User myUser = UserUtils.copyUser(user2);
    myUser.setId(5L);
    when(userDao.findById(myUser.getId())).thenReturn(myUser);
    assertTrue(userService.isEmployee(myUser));
    assertFalse(userService.isCustomer(myUser));
  }

  // is customer

  @Test
  public void isCustomerTest() {
    assertTrue(userService.isCustomer(user3));
    assertFalse(userService.isEmployee(user3));
  }

  private void setUpUsers() {
    user1 = new User();
    user1.setName("Joshua Bloch");
    user1.setAddress("USA");
    user1.setLogin("xbloch");
    user1.setUserType(UserType.CUSTOMER);
    user1.setTotalFines(BigDecimal.ZERO);

    user2 = new User();
    user2.setName("John Good");
    user2.setAddress("4125 7th Ave, New York, NY 10022, USA");
    user2.setLogin("xgood");
    user2.setUserType(UserType.EMPLOYEE);
    user2.setTotalFines(BigDecimal.ZERO);

    user3 = new User();
    user3.setId(user3Id);
    user3.setName("Pete Green");
    user3.setAddress("123456 NW 24nd Ave. - Miami, Fl 33142, USA");
    user3.setLogin("xgreen");
    user3.setUserType(UserType.CUSTOMER);
    user3.setTotalFines(BigDecimal.ONE);
  }
}
