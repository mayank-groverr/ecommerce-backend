package practice.mayank.ecommerce.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.security.entity.Role;
import practice.mayank.ecommerce.security.entity.RoleEnum;
import practice.mayank.ecommerce.security.exceptions.RoleNotFoundException;
import practice.mayank.ecommerce.security.repository.RoleRepository;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Component
public class RoleService {
    private final RoleRepository roleRepository;

    public void assignRole(User user, RoleEnum role) {
        user.setRole(getRole(role));
    }

    public static boolean hasRole(User user, RoleEnum roleName){
        return user.getRole().getRoleName().equals(roleName);
    }

    private Role getRole(RoleEnum roleName){
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        return role.orElseThrow(() -> new RoleNotFoundException("No such role found" + roleName));
    }


}
