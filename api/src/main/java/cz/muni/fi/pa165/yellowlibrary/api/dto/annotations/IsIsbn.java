package cz.muni.fi.pa165.yellowlibrary.api.dto.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates that the annotated field conforms to the ISBN-10 or ISBN-13 standard.
 *
 * The value can not contain any other characters other than numbers and character 'X' as used
 * in the ISBN-10 standard. No dashes are supported as ISBN value without dashes is valid and
 * the dashed version can not be easily reconstructed.
 * @author Norbert Slivka
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsIsbnValidator.class})
public @interface IsIsbn {
  String message() default
      "{cz.muni.fi.pa165.yellowlibrary.api.dto.annotations.IsIsbn.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
