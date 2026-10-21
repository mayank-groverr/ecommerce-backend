package practice.mayank.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import practice.mayank.ecommerce.validation.annotations.StrongPassword;

public record PasswordUpdateRequest(


        @StrongPassword
        String oldPassword,


        @StrongPassword
        String newPassword
) {
}
