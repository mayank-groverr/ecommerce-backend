package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.entity.Role;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Component
public class RoleService {
    private final RoleRepository roleRepository;

    /** Assigning single role to a user */
    public void assignRole(User user, String roleName) {
        Role role = getRole(roleName);
        user.getRoles().add(role);
    }

    /** Assigning multiple roles to a user */
    public void assignRole(User user, String... roleNames) {
        Set<Role> rolesInDB = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = getRole(roleName);
            rolesInDB.add(role);
        }
        user.getRoles().addAll(rolesInDB);
    }

    private Role getRole(String roleName){
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        return role.orElseThrow(() -> new ResourceNotFoundException("No such role found" + roleName));
    }


}
