package practice.mayank.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.repositories.UserRepository;
import java.util.Optional;


@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;


   private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   public void createNewUser(User user){
       user.getRoles().add("USER");
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
   }

   public void updateUser(User user){
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
   }

   public User findUserByEmail(String email){
       Optional<User> isPresent = userRepository.findById(email);
       return isPresent.orElse(null);
   }

   public void deleteUser(User user){
       userRepository.deleteById(user.getEmail());
   }
}
