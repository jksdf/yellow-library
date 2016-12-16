package cz.muni.fi.pa165.yellowlibrary.api.dto.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Norbert Slivka
 */
public class IsIsbnValidator implements ConstraintValidator<IsIsbn, String> {

  @Override
  public void initialize(IsIsbn constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    // TODO(slivka): custom error message
    if (value.matches("^[0-9X]{10}$")) {
      int s = 0, t = 0;
      for (char c : value.toCharArray()) {
        t += c == 'X' || c == 'x' ? 10 : Character.digit(c, 10);
        s += t;
      }
      return s % 11 == 0;
    }
    if (value.matches("^[0-9]{13}$")) {
      int s = 0;
      for (int i = 0; i < 13; i++) {
        s += (i % 2 == 0 ? 1 : 3) * Character.digit(value.charAt(i), 10);
      }
      return s % 10 == 0;
    }
    return false;
  }
}
