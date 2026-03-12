package practice.mayank.ecommerce.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.security.SecurityTokenResponse;
import practice.mayank.ecommerce.dto.user.LoginRequest;
import practice.mayank.ecommerce.dto.user.UserRequest;
import practice.mayank.ecommerce.dto.user.UserResponse;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.security.JwtService;
import practice.mayank.ecommerce.security.SecurityTokenService;
import practice.mayank.ecommerce.service.UserService;


@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;
    private final SecurityTokenService securityTokenService;

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
    public ResponseEntity<SecurityTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        User user = userService.authenticate(loginRequest);
        SecurityTokenResponse securityTokenResponse = securityTokenService.generateSecurityToken(user, response);
        return ResponseEntity.ok(securityTokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SecurityTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        SecurityTokenResponse refreshTokenResponse =
                securityTokenService.renewAccessTokenAndRefreshToken(request, response);
        return new ResponseEntity<>(refreshTokenResponse, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        securityTokenService.logoutUser(request, response);
        return ResponseEntity.noContent().build();
    }
}
