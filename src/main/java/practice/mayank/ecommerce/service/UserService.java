package practice.mayank.ecommerce.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.user.*;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.UserMapper;
import practice.mayank.ecommerce.repository.UserRepository;
import practice.mayank.ecommerce.util.PatchUtil;
import practice.mayank.ecommerce.validation.handler.CustomValidationHandler;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final CartService cartService;
    private final CustomValidationHandler validator;

    public UserResponse getUser(String email) {
        User userInDb = findUserByEmail(email);
        return userMapper.userToUserResponse(userInDb);
    }

    @Transactional
    public UserResponse createNewUser(UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        roleService.assignRole("ROLE_USER", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        cartService.createNewCart(newUser);
        return userMapper.userToUserResponse(newUser);
    }

    @Transactional
    public UserResponse createNewAdmin(UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        roleService.assignRole(user, "ROLE_USER", "ROLE_ADMIN");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        cartService.createNewCart(newUser);
        return userMapper.userToUserResponse(newUser);
    }

    @Transactional
    public UserResponse updateUser(String email, JsonPatch jsonPatch) {
        User userByEmail = findUserByEmail(email);
        UserUpdateRequest updateRequest = userMapper.userToUserRequestWithoutPassword(userByEmail);

        UserUpdateRequest requestAfterChanges =
                PatchUtil.applyJsonPatch(jsonPatch, updateRequest, UserUpdateRequest.class);

        validator.validate(requestAfterChanges);
        userMapper.updateExistingUser(requestAfterChanges, userByEmail);
        return userMapper.userToUserResponse(userByEmail);
    }

    @Transactional
    public void updateUserPassword(String email, PasswordUpdateRequest passwordUpdateRequest){
        User userByEmail = findUserByEmail(email);
        userByEmail.setPassword(passwordEncoder.encode(passwordUpdateRequest.password()));
    }

    @Transactional
    public void deleteUser(String email) {
        User userByEmail = findUserByEmail(email);
        userRepository.delete(userByEmail);
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
        return byEmail.orElseThrow(() -> new ResourceNotFoundException("No user found:" + email));
    }

    public User getPrincipalInstanceOfUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
