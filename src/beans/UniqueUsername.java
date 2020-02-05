package beans;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.annotations.Target;

//@Target({ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserNameValidator.class)
public @interface UniqueUsername {
    String message() default "This username is already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}