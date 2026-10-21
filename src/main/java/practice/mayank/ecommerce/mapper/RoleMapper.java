package practice.mayank.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import practice.mayank.ecommerce.security.entity.Role;
import practice.mayank.ecommerce.security.entity.RoleEnum;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Named("mapRoleToRoleEnum")
    default RoleEnum mapRoleSetToRoleEnumSet(Role role){
        return role.getRoleName();
    }

}
