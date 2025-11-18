package practice.mayank.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        if (user != null) {
            userService.createNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/email/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        User userByEmail = userService.findUserByEmail(email);
        if(userByEmail != null){
            userByEmail.setUserName(user.getUserName());
            userByEmail.setMobileNumber(user.getMobileNumber());
            userByEmail.setPassword(user.getPassword());
            userService.updateUser(user);
            return new ResponseEntity<>(userByEmail,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String email) {
        User userByEmail = userService.findUserByEmail(email);
        if(userByEmail != null){
            userService.deleteUser(userByEmail);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
