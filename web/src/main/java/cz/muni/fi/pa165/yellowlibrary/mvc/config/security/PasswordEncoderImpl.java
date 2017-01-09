package cz.muni.fi.pa165.yellowlibrary.mvc.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author Jozef Zivcic
 */
public class PasswordEncoderImpl implements PasswordEncoder {

  @Override
  public String encode(CharSequence password) {
    final int SALT_BYTE_SIZE = 24;
    final int HASH_BYTE_SIZE = 24;
    final int PBKDF2_ITERATIONS = 1000;
    // Generate a random salt
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_BYTE_SIZE];
    random.nextBytes(salt);
    // Hash the password
    byte[] hash = pbkdf2(password.toString().toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
    // format iterations:salt:hash
    return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    if(rawPassword == null) return false;
    if(encodedPassword == null) throw new IllegalArgumentException("password hash is null");
    String[] params = encodedPassword.split(":");
    int iterations = Integer.parseInt(params[0]);
    byte[] salt = fromHex(params[1]);
    byte[] hash = fromHex(params[2]);
    byte[] testHash = pbkdf2(rawPassword.toString().toCharArray(), salt, iterations, hash.length);
    return slowEquals(hash, testHash);
  }

  /**
   * Generates secure hash for password from parameters.
   *
   * @param password User password.
   * @param salt Additional string, which is included in hashing.
   * @param iterations Number of iterations.
   * @param bytes Length of key in bytes.
   * @return Raw key bytes.
   */
  private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
    try {
      PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
      return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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

  /**
   * Converts hexadecimal number stored in String into array of bytes.
   *
   * @param hex Number which is to be converted.
   * @return Array of decimal numbers.
   */
  private static byte[] fromHex(String hex) {
    byte[] binary = new byte[hex.length() / 2];
    for (int i = 0; i < binary.length; i++) {
      binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return binary;
  }

  /**
   * Converts array of bytes containing decimal numbers into hexadecimal number stored in String.
   *
   * @param array Array of bytes, which is to be converted.
   * @return Hexadecimal number.
   */
  private static String toHex(byte[] array) {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
  }
}
