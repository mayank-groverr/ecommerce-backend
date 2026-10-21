package practice.mayank.ecommerce.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.mayank.ecommerce.security.entity.Permission;
import practice.mayank.ecommerce.security.entity.PermissionEnum;

import java.util.Collection;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    Set<Permission> findByPermissionNameIn(Collection<PermissionEnum> names);

}
