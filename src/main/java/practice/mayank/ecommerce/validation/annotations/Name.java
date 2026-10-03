package practice.mayank.ecommerce.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "Name cannot be blank")
@Pattern(
        regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$",
        message = "Name must contain only letters and may include single spaces between words."
)
@Constraint(validatedBy = {})
public @interface Name {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
