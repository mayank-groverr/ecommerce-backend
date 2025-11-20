package practice.mayank.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PatchMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userByEmail = userService.findUserByEmail(auth.getName());
        if(userByEmail != null){
            userByEmail.setName(user.getName());
            userByEmail.setMobileNumber(user.getMobileNumber());
            userByEmail.setPassword(user.getPassword());
            userService.updateUser(userByEmail);
            return new ResponseEntity<>(userByEmail,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userByEmail = userService.findUserByEmail(auth.getName());
        if(userByEmail != null){
            userService.deleteUser(userByEmail);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
