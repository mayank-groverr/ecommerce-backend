package practice.mayank.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.entities.User;
import practice.mayank.ecommerce.repositories.UserRepository;
import java.util.Optional;


@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;

   public void createNewUser(User user){
       userRepository.save(user);
   }

   public boolean updateUser(User user){
       userRepository.save(user);
       return true;
   }

   public User findUserByEmail(String email){
       Optional<User> isPresent = userRepository.findById(email);
       return isPresent.orElse(null);
   }

   public void deleteUser(User user){
       userRepository.delete(user);
   }
}
