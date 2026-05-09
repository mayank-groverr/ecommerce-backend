package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.repository.UserRepository;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
    }
}
