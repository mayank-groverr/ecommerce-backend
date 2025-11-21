package practice.mayank.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import practice.mayank.ecommerce.dtos.request.UserRequest;
import practice.mayank.ecommerce.dtos.response.UserResponse;
import practice.mayank.ecommerce.entities.User;

@Mapper(componentModel = "spring")
public interface GenericMapper {
    UserResponse userToUserResponse(User user);
    User userRequestToUser(UserRequest user);

}
