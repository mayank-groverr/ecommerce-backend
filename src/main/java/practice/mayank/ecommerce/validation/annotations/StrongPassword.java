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
@NotBlank(message = "Password cannot be blank")
@Pattern(
        regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])\\S{8,16}$",
        message = """
                Password must be 8-16 characters long and include at least one uppercase letter,
                one lowercase letter, one number, and one special character (#?!@$%^&*-), and must not contain spaces."""
)

@Constraint(validatedBy = {})
public @interface StrongPassword {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
