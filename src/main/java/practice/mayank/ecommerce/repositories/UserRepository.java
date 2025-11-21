package practice.mayank.ecommerce.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import practice.mayank.ecommerce.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
