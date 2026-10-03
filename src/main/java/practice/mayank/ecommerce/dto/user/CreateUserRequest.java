package practice.mayank.ecommerce.dto.user;

import practice.mayank.ecommerce.validation.annotations.Email;
import practice.mayank.ecommerce.validation.annotations.Name;
import practice.mayank.ecommerce.validation.annotations.StrongPassword;

public record CreateUserRequest(

        @Name
        String name,

        @Email
        String userEmail,

        String mobileNumber,

        @StrongPassword
        String password
) {


}
