package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.entity.Role;
import practice.mayank.ecommerce.repository.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Component
public class RoleService {
    private final RoleRepository roleRepository;

    public Role makeUser() {
        Optional<Role> userRole = roleRepository.findByRoleName("ROLE_USER");
        return userRole.orElse(null);
    }

    public Role makeAdmin() {
        Optional<Role> userRole = roleRepository.findByRoleName("ROLE_ADMIN");
        return userRole.orElse(null);
    }
}
