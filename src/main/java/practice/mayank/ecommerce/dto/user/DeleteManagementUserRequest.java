package practice.mayank.ecommerce.dto.user;


import practice.mayank.ecommerce.validation.annotations.Email;

public record DeleteManagementUserRequest(
        @Email
        String email
) {
}
