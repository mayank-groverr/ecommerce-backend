package practice.mayank.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserEmail(String email);
}
