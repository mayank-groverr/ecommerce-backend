package practice.mayank.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Role;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String name);
}
