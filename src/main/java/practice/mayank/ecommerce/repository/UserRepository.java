package practice.mayank.ecommerce.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import practice.mayank.ecommerce.entity.User;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByEmail(String email);
}
