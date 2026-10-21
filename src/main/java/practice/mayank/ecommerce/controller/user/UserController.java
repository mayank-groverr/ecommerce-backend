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
import practice.mayank.ecommerce.mapper.UserMapper;
import practice.mayank.ecommerce.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<UserResponse> getDetail() {
        UserResponse user = userService.getUser();
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "/update", consumes = "application/json-patch+json")
    public ResponseEntity<UserResponse> updateUser(@RequestBody JsonPatch jsonPatch) {
        UserResponse updatedUser = userService.updateUser(jsonPatch);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        userService.updateUserPassword(passwordUpdateRequest);
        return new ResponseEntity<>("Password Updated Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}
