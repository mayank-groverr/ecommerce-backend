package practice.mayank.ecommerce.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.dtos.request.UserRequest;
import practice.mayank.ecommerce.dtos.response.UserResponse;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.mapper.GenericMapper;
import practice.mayank.ecommerce.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericMapper genericMapper;


    public UserResponse getUser(String email) {
        User finalUser = findUserByEmail(email);
        return genericMapper.userToUserResponse(finalUser);
    }

    public UserResponse createNewUser(UserRequest userRequest) {
        User finalUser = genericMapper.userRequestToUser(userRequest);
        finalUser.getRoles().add("USER");
        finalUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(finalUser);
        return genericMapper.userToUserResponse(finalUser);
    }

    public UserResponse createNewAdmin(UserRequest userRequest) {
        User finalUser = genericMapper.userRequestToUser(userRequest);
        finalUser.getRoles().add("USER");
        finalUser.getRoles().add("ADMIN");
        finalUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(finalUser);
        return genericMapper.userToUserResponse(finalUser);
    }

    public UserResponse updateUser(String email, UserRequest userRequest) {
        try {
            User userInDb = findUserByEmail(email);
            userInDb.setName((userRequest.getName() != null && !userRequest.getName().isEmpty()) ? userRequest.getName() : userInDb.getName());
            userInDb.setEmail((userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) ? userRequest.getEmail() : userInDb.getEmail());
            userInDb.setMobileNumber((userRequest.getMobileNumber() != null && !userRequest.getMobileNumber().isEmpty()) ? userRequest.getMobileNumber() : userInDb.getMobileNumber());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                userInDb.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            userRepository.save(userInDb);
            return genericMapper.userToUserResponse(userInDb);
        } catch (Exception e) {
            return null;
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean deleteUser(String email) {
        try {
            User userInDb = findUserByEmail(email);
            userRepository.delete(userInDb);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
