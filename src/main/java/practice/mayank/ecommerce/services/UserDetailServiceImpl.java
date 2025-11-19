package practice.mayank.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.repositories.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByemail(email);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("User Not Found: " + email);
    }
}
