package practice.mayank.ecommerce.dto.user;

import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.security.entity.RoleEnum;

import java.util.Set;

public record ManagementUserResponse (
        String name,
        String userEmail,
        String mobileNumber,
        RoleEnum role,
        Set<PermissionEnum> permissions
){
}
