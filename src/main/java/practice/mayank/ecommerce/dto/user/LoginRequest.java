package practice.mayank.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @NotBlank(message = "Email cannot be blank")
        @Email
        String userEmail,

        @NotBlank(message = "Password cannot be blank")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$",
                message = """
                Password must be 8-16 characters long and include at least one uppercase letter,
                one lowercase letter, one number, and one special character (#?!@$%^&*-)."""
        )
        String password
) {
}
