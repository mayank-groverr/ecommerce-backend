package practice.mayank.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @NotBlank(message = "Name cannot be blank")
        @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$",
                message = "Name must contain only letters and may include single spaces between words.")
        String name,

        @Email(message = "Provide a valid email address")
        @NotBlank(message = "Email cannot be blank")
        String userEmail,

        String mobileNumber


) {


}
