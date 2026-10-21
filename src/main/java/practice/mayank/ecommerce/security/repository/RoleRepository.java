package practice.mayank.ecommerce.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.security.entity.Role;
import practice.mayank.ecommerce.security.entity.RoleEnum;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(RoleEnum role);
}
