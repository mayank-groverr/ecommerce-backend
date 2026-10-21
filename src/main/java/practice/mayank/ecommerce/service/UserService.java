package practice.mayank.ecommerce.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.user.*;
import practice.mayank.ecommerce.entity.*;
import practice.mayank.ecommerce.exception.customexception.ActionNotAllowedException;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.UserMapper;
import practice.mayank.ecommerce.repository.UserRepository;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.security.entity.RoleEnum;
import practice.mayank.ecommerce.security.services.AuthenticatedUserProvider;
import practice.mayank.ecommerce.security.services.PermissionService;
import practice.mayank.ecommerce.security.services.RoleService;
import practice.mayank.ecommerce.util.PatchUtil;
import practice.mayank.ecommerce.validation.handler.CustomValidationHandler;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final CartService cartService;
    private final CustomValidationHandler validator;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final Set<PermissionEnum> blockedPermissions = Set.of(
            PermissionEnum.CREATE_ADMIN,
            PermissionEnum.MANAGE_EMPLOYEE
    );

    public UserResponse getUser(){
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        return userMapper.userToUserResponse(authenticatedUser);
    }

    public ManagementUserResponse getManagementUser(){
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        return userMapper.userToManagementUserResponse(authenticatedUser);
    }

    @Transactional
    public UserResponse createBuyerAccount(CreateUserRequest userRequest) {
        User unSavedUser = userMapper.createUser(userRequest);
        User savedUser = createNewUser(unSavedUser, RoleEnum.USER);
        cartService.createNewCart(savedUser);
        return userMapper.userToUserResponse(savedUser);
    }

    @Transactional
    public ManagementUserResponse createAdminAccount(CreateAdminRequest createAdminRequest) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        PermissionService.hasPermission(authenticatedUser, PermissionEnum.CREATE_ADMIN);
        User unsavedAdmin = userMapper.createUser(createAdminRequest);
        unsavedAdmin.setCreatedByEmail(authenticatedUser.getUserEmail());
        User savedAdmin = createNewUser(unsavedAdmin, RoleEnum.ADMIN);
        permissionService.assignAll(savedAdmin);
        return userMapper.userToManagementUserResponse(savedAdmin);

    }

    @Transactional
    public ManagementUserResponse createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        PermissionService.hasPermission(authenticatedUser, PermissionEnum.MANAGE_EMPLOYEE);
        if (!RoleService.hasRole(authenticatedUser, RoleEnum.ADMIN)
                && createEmployeeRequest.permissions() != null &&
                permissionService.containsBlockedPermissions
                        (createEmployeeRequest.permissions(), blockedPermissions)
        ) {
            throw new ActionNotAllowedException("Permissions not allowed: " + blockedPermissions);
        }

        User unsavedEmployee = userMapper.createUser(createEmployeeRequest);
        unsavedEmployee.setCreatedByEmail(authenticatedUser.getUserEmail());
        User savedEmployee = createNewUser(unsavedEmployee, RoleEnum.EMPLOYEE);
        permissionService.assignSpecific(savedEmployee, createEmployeeRequest.permissions());
        return userMapper.userToManagementUserResponse(savedEmployee);
    }

    private User createNewUser(User user, RoleEnum role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleService.assignRole(user, role);
        return userRepository.save(user);
    }

    @Transactional
    public ManagementUserResponse updateManagementUser(JsonPatch jsonPatch) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        User updatedUser = applyUpdate(authenticatedUser.getUserEmail(), jsonPatch);
        return userMapper.userToManagementUserResponse(updatedUser);
    }

    @Transactional
    public UserResponse updateUser(JsonPatch jsonPatch) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        User updatedUser = applyUpdate(authenticatedUser.getUserEmail(), jsonPatch);
        return userMapper.userToUserResponse(updatedUser);
    }

    private User applyUpdate(String email, JsonPatch jsonPatch) {
        User userByEmail = findUserByEmail(email);
        SelfUpdateRequest selfUpdateRequest = userMapper.userToSelfUpdateRequest(userByEmail);
        SelfUpdateRequest requestAfterChanges =
                PatchUtil.applyJsonPatch(jsonPatch, selfUpdateRequest, SelfUpdateRequest.class);
        validator.validate(requestAfterChanges);
        userMapper.updateExistingUser(requestAfterChanges, userByEmail);
        return userByEmail;
    }


    @Transactional
    public ManagementUserResponse updateEmployeePermission(UpdateEmployeePermissionRequest
                                                                   updateEmployeePermissionRequest) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        PermissionService.hasPermission(authenticatedUser, PermissionEnum.MANAGE_EMPLOYEE);

        if (authenticatedUser.getUserEmail().equals(updateEmployeePermissionRequest.email())) {
            throw new AccessDeniedException("You cannot update your own permissions");
        }

        if (!RoleService.hasRole(authenticatedUser, RoleEnum.ADMIN) &&
                updateEmployeePermissionRequest.grant() != null && permissionService.containsBlockedPermissions
                (updateEmployeePermissionRequest.grant(), blockedPermissions)) {
            throw new AccessDeniedException("Permissions cannot be allowed: " + blockedPermissions);
        }

        User targetUser = findUserByEmail(updateEmployeePermissionRequest.email());
        validateCanManageUser(authenticatedUser, targetUser);


        if (RoleService.hasRole(targetUser, RoleEnum.EMPLOYEE) ||
                RoleService.hasRole(targetUser, RoleEnum.ADMIN)
        ) {
            if (updateEmployeePermissionRequest.grant() != null) {
                permissionService.grant(targetUser, updateEmployeePermissionRequest.grant());
            }
            if (updateEmployeePermissionRequest.revoke() != null) {
                permissionService.revoke(targetUser, updateEmployeePermissionRequest.revoke());
            }
        } else {
            throw new ActionNotAllowedException("Permission update is not supported");
        }

        return userMapper.userToManagementUserResponse(targetUser);
    }


    @Transactional
    public void updateUserPassword(PasswordUpdateRequest passwordUpdateRequest) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        if (!passwordEncoder.matches(passwordUpdateRequest.oldPassword(), authenticatedUser.getPassword())) {
            throw new ActionNotAllowedException("Incorrect old password");
        }
        authenticatedUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.newPassword()));
    }

    @Transactional
    public void deleteUser() {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        if (RoleService.hasRole(authenticatedUser, RoleEnum.USER)) {
            userRepository.delete(authenticatedUser);
        } else {
            throw new ActionNotAllowedException("Account cannot be deleted");
        }
    }


    @Transactional
    public void deleteManagementUser(DeleteManagementUserRequest deleteManagementUser) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        PermissionService.hasPermission(authenticatedUser, PermissionEnum.MANAGE_EMPLOYEE);
        User userToBeDeleted = findUserByEmail(deleteManagementUser.email());
        validateCanManageUser(authenticatedUser, userToBeDeleted);
        if (RoleService.hasRole(userToBeDeleted, RoleEnum.EMPLOYEE) ||
                RoleService.hasRole(userToBeDeleted, RoleEnum.ADMIN)
        ) {
            userRepository.delete(userToBeDeleted);
        } else {
            throw new ActionNotAllowedException("Account cannot be deleted");
        }
    }

    public User authenticate(LoginRequest loginRequest) {
        Authentication authenticateUser =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.userEmail(),
                                loginRequest.password()
                        )
                );
        return (User) authenticateUser.getPrincipal();
    }

    public User findUserByEmail(String email) {
        Optional<User> byEmail = userRepository.findByUserEmail(email);
        return byEmail.orElseThrow(() -> new ResourceNotFoundException("No user found:" + email));
    }


    private void validateCanManageUser(User authenticatedUser, User targetUser) {

        boolean authIsAdmin = RoleService.hasRole(authenticatedUser, RoleEnum.ADMIN);
        boolean targetIsAdmin = RoleService.hasRole(targetUser, RoleEnum.ADMIN);

        if (targetIsAdmin) {
            if (!authIsAdmin) {
                throw new ActionNotAllowedException("Action Denied: You cannot manage an Administrator.");
            }

            if (!authenticatedUser.getUserEmail().equals(targetUser.getCreatedByEmail())) {
                throw new ActionNotAllowedException("Action Denied: Admins cannot manage peer Admins.");
            }
        }
    }

}
