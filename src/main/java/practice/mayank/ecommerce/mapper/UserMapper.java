package practice.mayank.ecommerce.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import practice.mayank.ecommerce.dto.user.*;
import practice.mayank.ecommerce.entity.User;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class, RoleMapper.class})
public interface UserMapper{

    User createUser(CreateAdminRequest createAdminRequest);

    @Mapping(target = "permissions", ignore = true)
    User createUser(CreateEmployeeRequest createEmployeeRequest);

    User createUser(CreateUserRequest userRequest);

    UserResponse userToUserResponse(User user);

    SelfUpdateRequest userToSelfUpdateRequest(User user);

    void updateExistingUser(SelfUpdateRequest updateRequest, @MappingTarget User user);

    @Mapping(
            target =  "permissions" ,
            source = "permissions" ,
            qualifiedByName = "mapPermissionSetToPermissionEnumSet"
    )
    @Mapping(
            target =  "role" ,
            source = "role" ,
            qualifiedByName = "mapRoleToRoleEnum"
    )
    ManagementUserResponse userToManagementUserResponse(User user);
}
