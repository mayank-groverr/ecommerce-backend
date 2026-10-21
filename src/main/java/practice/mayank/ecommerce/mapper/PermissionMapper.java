package practice.mayank.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import practice.mayank.ecommerce.security.entity.Permission;
import practice.mayank.ecommerce.security.entity.PermissionEnum;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PermissionMapper {


    @Named("mapPermissionSetToPermissionEnumSet")
    default Set<PermissionEnum> mapPermissionToPermissionEnum(Set<Permission> value){
        return value.stream().map(Permission::getPermissionName).collect(Collectors.toSet());
    }

    @Named("mapPermissionEnumSetToPermissionSet")
    default Set<Permission> mapPermissionEnumSetToPermissionSet(Set<PermissionEnum> value){
        return value.stream().map(Permission::new).collect(Collectors.toSet());
    }

}
