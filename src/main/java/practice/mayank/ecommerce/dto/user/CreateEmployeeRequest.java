package practice.mayank.ecommerce.dto.user;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.validation.annotations.Email;
import practice.mayank.ecommerce.validation.annotations.Name;
import practice.mayank.ecommerce.validation.annotations.StrongPassword;


import java.util.Set;

public record CreateEmployeeRequest(
        @Name
        String name,

        @Email
        String userEmail,

        String mobileNumber,

        @StrongPassword
        String password,

        @Size(min = 1,message =  "Either permissions can be null or a list of allowed permission")
        Set<@NotNull(message = "null is not allowed inside list") PermissionEnum> permissions
) {


}
