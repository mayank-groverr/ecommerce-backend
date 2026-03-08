package practice.mayank.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.user.LoginRequest;
import practice.mayank.ecommerce.dto.user.UserRequest;
import practice.mayank.ecommerce.dto.user.UserResponse;
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
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(
            @Valid
            @RequestBody UserRequest userRequest
    ) {
        UserResponse newUser = userService.createNewUser(userRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Request with Credentials -> Verify -> Return token if valid
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest);
        String token = jwtService.generateToken(user.getUserEmail());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
