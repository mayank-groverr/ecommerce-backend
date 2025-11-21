package practice.mayank.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dtos.request.UserRequest;
import practice.mayank.ecommerce.dtos.response.UserResponse;
import practice.mayank.ecommerce.services.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createNewAdmin(@RequestBody UserRequest user) {
        UserResponse newAdmin = userService.createNewAdmin(user);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateDetails(@RequestBody UserRequest user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserResponse userResponse = userService.updateUser(auth.getName(), user);
        if (userResponse != null) {
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userService.deleteUser(auth.getName())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
