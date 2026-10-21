package practice.mayank.ecommerce.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.repository.UserRepository;
import practice.mayank.ecommerce.service.UserService;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserProvider {

    private final UserRepository userRepository;

    public static String getAuthenticatedUserEmailFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        throw new AccessDeniedException("User is not authenticated");
    }

    public User getAuthenticatedUser() {
        String email = getAuthenticatedUserEmailFromSecurityContext();
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found:" + email));
    }
}