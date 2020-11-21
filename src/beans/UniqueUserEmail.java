package beans;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

//@Target({ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserEmailValidator.class)
public @interface UniqueUserEmail {
    String message() default "This Email is already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}