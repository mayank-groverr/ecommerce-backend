package practice.mayank.ecommerce.controller.management;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.user.CreateEmployeeRequest;
import practice.mayank.ecommerce.dto.user.DeleteManagementUserRequest;
import practice.mayank.ecommerce.dto.user.ManagementUserResponse;
import practice.mayank.ecommerce.dto.user.UpdateEmployeePermissionRequest;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.service.UserService;

@RestController
@RequestMapping("/management/employee")
@RequiredArgsConstructor
public class EmployeeManagementController {

    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<ManagementUserResponse> createNewEmployee(
            @Valid @RequestBody CreateEmployeeRequest createEmployeeRequest
    ){
        ManagementUserResponse newUser = userService.createEmployee(createEmployeeRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PatchMapping("/update-permission")
    public ResponseEntity<ManagementUserResponse> updateEmployeePermission(
            @Valid @RequestBody UpdateEmployeePermissionRequest updateEmployeePermissionRequest
            ){
        ManagementUserResponse managementUserResponse = userService.updateEmployeePermission(updateEmployeePermissionRequest);
        return ResponseEntity.ok(managementUserResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser(
            @Valid
            @RequestBody
            DeleteManagementUserRequest deleteManagementUser
    ){
        userService.deleteManagementUser(deleteManagementUser);
        return ResponseEntity.noContent().build();
    }


}
