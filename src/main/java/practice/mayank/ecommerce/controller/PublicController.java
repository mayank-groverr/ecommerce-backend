package practice.mayank.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.LoginRequest;
import practice.mayank.ecommerce.dto.UserRequest;
import practice.mayank.ecommerce.dto.UserResponse;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.security.JwtService;
import practice.mayank.ecommerce.service.UserService;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRequest userRequest){
        UserResponse newUser = userService.createNewUser(userRequest);
        if (newUser != null){
            return new ResponseEntity<>(newUser,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Request with Credentials -> Verify -> Return token if valid
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){

        User user = userService.authenticate(loginRequest);
        if(user != null){
            String token = jwtService.generateToken(user.getEmail());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
