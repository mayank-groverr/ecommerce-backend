package practice.mayank.ecommerce.dto.user;

import practice.mayank.ecommerce.validation.annotations.Email;
import practice.mayank.ecommerce.validation.annotations.StrongPassword;

public record LoginRequest(
        @Email
        String userEmail,

        @StrongPassword
        String password
) {
}
