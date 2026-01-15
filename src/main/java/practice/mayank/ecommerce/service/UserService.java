package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.dto.LoginRequest;
import practice.mayank.ecommerce.dto.UserRequest;
import practice.mayank.ecommerce.dto.UserResponse;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.mapper.GenericMapper;
import practice.mayank.ecommerce.repository.UserRepository;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GenericMapper genericMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final CartService cartService;

    public UserResponse getUser(String email) {
        User userInDb = findUserByEmail(email);
        return genericMapper.userToUserResponse(userInDb);
    }

    public UserResponse createNewUser(UserRequest userRequest) {
        User user = genericMapper.userRequestToUser(userRequest);
        user.getRoles().add(roleService.makeUser());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        cartService.createNewCart(user);
        return genericMapper.userToUserResponse(newUser);
    }

    public UserResponse createNewAdmin(UserRequest userRequest) {
        User user = genericMapper.userRequestToUser(userRequest);
        user.getRoles().add(roleService.makeUser());
        user.getRoles().add(roleService.makeAdmin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        cartService.createNewCart(user);
        return genericMapper.userToUserResponse(newUser);
    }

    public UserResponse updateUser(String email, UserRequest userRequest) {
        User user = genericMapper.userRequestToUser(userRequest);
        User userInDb = findUserByEmail(email);
        if (userInDb != null) {
            userInDb.setName((user.getName() != null && !user.getName().isEmpty()) ? user.getName() : userInDb.getName());
            userInDb.setMobileNumber((user.getMobileNumber() != null && !user.getMobileNumber().isEmpty()) ? user.getMobileNumber() : userInDb.getMobileNumber());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(userInDb);
            return genericMapper.userToUserResponse(userInDb);
        }

        return null;
    }

    public boolean deleteUser(String email) {
        User user = findUserByEmail(email);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public User authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.userEmail(),
                        loginRequest.password()));

        return findUserByEmail(loginRequest.userEmail());

    }

    private User findUserByEmail(String email) {
        Optional<User> byEmail = userRepository.findByUserEmail(email);
        return byEmail.orElse(null);
    }

}
