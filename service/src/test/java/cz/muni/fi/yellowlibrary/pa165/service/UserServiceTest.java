package cz.muni.fi.yellowlibrary.pa165.service;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.UserDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;
import cz.muni.fi.yellowlibrary.pa165.service.configuration.ServiceConfiguration;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;


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

  private User user1;
  private User user2;
  private User user3;

  public static final Long userId = 10L;

  @BeforeMethod
  public void setUpUsers() {
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
    user3.setId(1L);
    user3.setName("Pete Green");
    user3.setAddress("123456 NW 24nd Ave. - Miami, Fl 33142, USA");
    user3.setLogin("xgreen");
    user3.setUserType(UserType.CUSTOMER);
    user3.setTotalFines(BigDecimal.ONE);
  }

  @BeforeMethod
  public void setUpMocks() {
    MockitoAnnotations.initMocks(this);
    when(userDao.findById(1L)).thenReturn(user3);
    when(userDao.findAllUsers()).thenReturn(Arrays.asList(user1, user2));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null)
        throw new NullPointerException("Argument cannot be null");
      User user = (User) arg;
      if (user.getId() != null)
        throw new IllegalArgumentException("User id must be null");
      user.setId(userId);
      return null;
    }).when(userDao).createUser(any(User.class));

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
    assertEquals(userId, user1.getId());
  }

  //tests on findById

  @Test
  public void findByNonExistingIdTest() {
    User user = userService.findById(3L);
    assertNull(user);
  }

  @Test
  public void findByExistingIdsTest() {
    User user = userService.findById(1L);
    assertDeepEquals(user, user3);
  }

  private void assertDeepEquals(User actual, User expected) {
    assertEquals(actual.getId(), expected.getId());
    assertEquals(actual.getLogin(), expected.getLogin());
    assertEquals(actual.getName(), expected.getName());
    assertEquals(actual.getAddress(), expected.getAddress());
    assertEquals(actual.getTotalFines(), expected.getTotalFines());
    assertEquals(actual.getLoans(), expected.getLoans());
    assertEquals(actual.getUserType(), expected.getUserType());
  }
}