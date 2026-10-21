package practice.mayank.ecommerce.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.security.entity.Permission;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.exception.customexception.ActionNotAllowedException;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.security.exceptions.PermissionDeniedException;
import practice.mayank.ecommerce.security.repository.PermissionRepository;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;


    public void assignAll(User user){
        user.setPermissions(getAll());
    }


    public void assignSpecific(User user, Set<PermissionEnum> requestedPermissionNames){
        if (requestedPermissionNames == null || requestedPermissionNames.isEmpty()){
            return;
        }
        user.getPermissions().addAll(getAll(requestedPermissionNames));
    }


    private Set<Permission> getAll(){
        return new HashSet<>(permissionRepository.findAll());
    }

    private Set<Permission> getAll(Set<PermissionEnum> requestedPermissionNames){

        Set<Permission> byName = permissionRepository.findByPermissionNameIn(requestedPermissionNames);
        if (byName.size() != requestedPermissionNames.size()) {
            Set<PermissionEnum> foundNames = byName.stream()
                    .map(Permission::getPermissionName)
                    .collect(Collectors.toSet());

            Set<PermissionEnum> missing = new HashSet<>(requestedPermissionNames);
            missing.removeAll(foundNames);

            throw new ResourceNotFoundException("No such Permissions Found: %s".formatted(missing));
        }

        return byName;
    }


    public boolean containsBlockedPermissions(Set<PermissionEnum> permissionList , Set<PermissionEnum> blockedPermissions){

        Objects.requireNonNull(permissionList, "Permission list is null");
        Objects.requireNonNull(blockedPermissions, "Blocked Permission list is null");
        return !Collections.disjoint(permissionList, blockedPermissions);
    }

    public void grant(User user, Set<PermissionEnum> grant) {
        if (grant == null || grant.isEmpty()) return;
        Set<Permission> permissionsToAssign = getAll(grant);
        user.getPermissions().addAll(permissionsToAssign);
    }

    public void revoke(User user, Set<PermissionEnum> revoke) {
        if (revoke == null || revoke.isEmpty()) return;
        Set<PermissionEnum> userPermissions = user.getPermissions().stream()
                .map(Permission::getPermissionName)
                .collect(Collectors.toSet());

        Set<PermissionEnum> missingFromUser = new HashSet<>(revoke);
        missingFromUser.removeAll(userPermissions);

        if (!missingFromUser.isEmpty()) {
            throw new ActionNotAllowedException("User doesn't have these permissions to revoke: " + missingFromUser);
        }

        user.getPermissions()
                .removeIf(permission -> revoke.contains(permission.getPermissionName()));
    }

    public static void hasPermission(User user, PermissionEnum permissionEnum){
         if (!user.getPermissions().contains(new Permission(permissionEnum))){
             throw new PermissionDeniedException("You do not have permission to perform this action");
        }
    }

}
