package practice.mayank.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
        @NotBlank(message = "Name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "Name must contain only letters and may include single spaces between words.")
        String name,

        @Email(message = "Provide a valid email address")
        @NotBlank(message = "Email cannot be blank")
        String userEmail,

        String mobileNumber,

        @NotBlank(message = "Password cannot be left null")
        @Pattern(
                regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])\\S{8,16}$",
                message = """
                Password must be 8-16 characters long and include at least one uppercase letter,
                one lowercase letter, one number, and one special character (#?!@$%^&*-), and must not contain spaces."""
        )
        String password
) {


}
