package practice.mayank.ecommerce.dto.user;

import practice.mayank.ecommerce.validation.annotations.Email;
import practice.mayank.ecommerce.validation.annotations.Name;

public record SelfUpdateRequest(
        @Name
        String name,

        @Email
        String userEmail,

        String mobileNumber


) {


}
