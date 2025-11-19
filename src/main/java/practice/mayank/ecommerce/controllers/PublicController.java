package practice.mayank.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.services.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        if (user != null) {
            userService.createNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
