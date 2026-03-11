package practice.mayank.ecommerce.controller.user;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.user.PasswordUpdateRequest;
import practice.mayank.ecommerce.dto.user.UserResponse;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<UserResponse> getDetail() {
        User authenticatedUser = userService.getAuthenticatedUser();
        UserResponse user = userService.getUser(authenticatedUser.getUserEmail()); // Authenticated user email
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "/update", consumes = "application/json-patch+json")
    public ResponseEntity<UserResponse> updateUser(@RequestBody JsonPatch jsonPatch) {
        User authenticatedUser = userService.getAuthenticatedUser();
        UserResponse updatedUser = userService.updateUser(authenticatedUser.getUserEmail(), jsonPatch);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        User authenticatedUser = userService.getAuthenticatedUser();
        userService.updateUserPassword(authenticatedUser.getUserEmail(), passwordUpdateRequest);
        return new ResponseEntity<>("Password Updated Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        userService.deleteUser(authenticatedUser.getUserEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
