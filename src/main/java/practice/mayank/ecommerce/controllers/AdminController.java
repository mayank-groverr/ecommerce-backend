package practice.mayank.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createNewAdmin(@RequestBody User user){
        if(user != null){
            user.getRoles().add("ADMIN");
            userService.createNewUser(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PatchMapping
    public ResponseEntity<User> updateDetails(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userByEmail = userService.findUserByEmail(auth.getName());
        if(userByEmail != null){
            userByEmail.setName(user.getName());
            userByEmail.setPassword(user.getPassword());
            userByEmail.setMobileNumber(user.getMobileNumber());
            userService.updateUser(userByEmail);
            return new ResponseEntity<>(userByEmail, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userByEmail = userService.findUserByEmail(auth.getName());
        if(userByEmail != null){
            userService.deleteUser(userByEmail);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
