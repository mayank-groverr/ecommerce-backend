package practice.mayank.ecommerce.controller.admin;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.user.PasswordUpdateRequest;
import practice.mayank.ecommerce.dto.user.UserRequest;
import practice.mayank.ecommerce.dto.user.UserResponse;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createNewAdmin(
            @Valid @RequestBody UserRequest userRequest
    ){
        UserResponse newUser = userService.createAdminAccount(userRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<UserResponse> getDetail() {
        User authenticatedUser = UserService.getAuthenticatedUser();
        UserResponse user = userService.getUser(authenticatedUser.getUserEmail()); // Authenticated user email
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "/update",  consumes = "application/json-patch+json")
    public ResponseEntity<UserResponse> updateUser(@RequestBody JsonPatch jsonPatch) {
        User authenticatedUser = UserService.getAuthenticatedUser();
        UserResponse updatedUser = userService.updateUser(authenticatedUser.getUserEmail(), jsonPatch);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        User authenticatedUser = UserService.getAuthenticatedUser();
        userService.updateUserPassword(authenticatedUser.getUserEmail(), passwordUpdateRequest);
        return new ResponseEntity<>("Password Updated Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser() {
        User authenticatedUser = UserService.getAuthenticatedUser();
        userService.deleteUser(authenticatedUser.getUserEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
