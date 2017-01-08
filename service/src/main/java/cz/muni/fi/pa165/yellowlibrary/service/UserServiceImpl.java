package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.UserDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;

/**
 * @author Jozef Zivcic
 */
@Service
public class UserServiceImpl implements UserService {

  @Inject
  UserDao userDao;

  @Override
  public void create(User user, String plainTextPassword) {
    if (plainTextPassword == null)
      throw new NullPointerException("Plaintext password cannot be null");
    if (plainTextPassword.trim().isEmpty())
      throw new IllegalArgumentException("Password cannot be empty");
    user.setPasswordHash(createHash(plainTextPassword));
    userDao.createUser(user);
  }

  @Override
  public boolean authenticate(User user, String plainTextPassword) {
    if (plainTextPassword == null)
      throw new NullPointerException("Plaintext password cannot be null");
    if (plainTextPassword.trim().isEmpty())
      throw new IllegalArgumentException("Password cannot be empty");
    return validatePassword(plainTextPassword, user.getPasswordHash());
  }

  @Override
  public void update(User user) {
    userDao.updateUser(user);
  }

  @Override
  public void delete(User user) {
    userDao.deleteUser(user);
  }

  @Override
  public User findById(Long id) {
    return userDao.findById(id);
  }

  @Override
  public User findByLogin(String login) {
    return userDao.findByLogin(login);
  }

  @Override
  public boolean isEmployee(User user) {
    return findById(user.getId()).isEmployee();
  }

  @Override
  public boolean isCustomer(User user) {
    return findById(user.getId()).isCustomer();
  }

  @Override
  public List<User> findAllUsers() {
    return userDao.findAllUsers();
  }

  @Override
  public List<User> findAllUsersWithName(String name) {
    return userDao.findAllUsersWithName(name);
  }

  //see  https://crackstation.net/hashing-security.htm#javasourcecode
  private static String createHash(String password) {
    final int SALT_BYTE_SIZE = 24;
    final int HASH_BYTE_SIZE = 24;
    final int PBKDF2_ITERATIONS = 1000;
    // Generate a random salt
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_BYTE_SIZE];
    random.nextBytes(salt);
    // Hash the password
    byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
    // format iterations:salt:hash
    return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
  }

  private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
    try {
      PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
      return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean validatePassword(String password, String correctHash) {
    if(password==null) return false;
    if(correctHash==null) throw new IllegalArgumentException("password hash is null");
    String[] params = correctHash.split(":");
    int iterations = Integer.parseInt(params[0]);
    byte[] salt = fromHex(params[1]);
    byte[] hash = fromHex(params[2]);
    byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
    return slowEquals(hash, testHash);
  }

  /**
   * Compares two byte arrays in length-constant time. This comparison method
   * is used so that password hashes cannot be extracted from an on-line
   * system using a timing attack and then attacked off-line.
   *
   * @param a the first byte array
   * @param b the second byte array
   * @return true if both byte arrays are the same, false if not
   */
  private static boolean slowEquals(byte[] a, byte[] b) {
    int diff = a.length ^ b.length;
    for (int i = 0; i < a.length && i < b.length; i++)
      diff |= a[i] ^ b[i];
    return diff == 0;
  }

  private static byte[] fromHex(String hex) {
    byte[] binary = new byte[hex.length() / 2];
    for (int i = 0; i < binary.length; i++) {
      binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return binary;
  }

  private static String toHex(byte[] array) {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
  }
}
