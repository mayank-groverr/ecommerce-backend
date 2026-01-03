package practice.mayank.ecommerce.mapper;

import org.mapstruct.Mapper;
import practice.mayank.ecommerce.dto.UserRequest;
import practice.mayank.ecommerce.dto.UserResponse;
import practice.mayank.ecommerce.entity.User;


@Mapper(componentModel = "spring")
public interface GenericMapper {

    UserResponse userToUserResponse (User user);

    User userRequestToUser(UserRequest userRequest);
}

