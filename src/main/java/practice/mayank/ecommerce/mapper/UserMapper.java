package practice.mayank.ecommerce.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import practice.mayank.ecommerce.dto.user.UserRequest;
import practice.mayank.ecommerce.dto.user.UserResponse;
import practice.mayank.ecommerce.dto.user.UserUpdateRequest;
import practice.mayank.ecommerce.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserResponse userToUserResponse(User user);




    UserUpdateRequest userToUserRequestWithoutPassword(User user);

    User userRequestToUser(UserRequest userRequest);


    void updateExistingUser(UserUpdateRequest updateRequest, @MappingTarget User user);
}
