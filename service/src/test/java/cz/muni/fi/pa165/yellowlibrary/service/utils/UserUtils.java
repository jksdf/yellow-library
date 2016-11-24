package cz.muni.fi.pa165.yellowlibrary.service.utils;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;

import static org.testng.Assert.assertEquals;

/**
 * @author Jozef Zivcic
 */
public class UserUtils {

  public static void assertDeepEquals(User actual, User expected) {
    assertEquals(actual.getId(), expected.getId());
    assertEquals(actual.getLogin(), expected.getLogin());
    assertEquals(actual.getPasswordHash(), expected.getPasswordHash());
    assertEquals(actual.getName(), expected.getName());
    assertEquals(actual.getAddress(), expected.getAddress());
    assertEquals(actual.getTotalFines(), expected.getTotalFines());
    assertEquals(actual.getLoans(), expected.getLoans());
    assertEquals(actual.getUserType(), expected.getUserType());
  }

  public static User copyUser(User user) {
    User newUser = new User();
    newUser.setId(user.getId());
    newUser.setLogin(user.getLogin());
    newUser.setPasswordHash(user.getPasswordHash());
    newUser.setName(user.getName());
    newUser.setAddress(user.getAddress());
    newUser.setTotalFines(user.getTotalFines());
    newUser.setLoans(user.getLoans());
    newUser.setUserType(user.getUserType());
    return newUser;
  }
}
