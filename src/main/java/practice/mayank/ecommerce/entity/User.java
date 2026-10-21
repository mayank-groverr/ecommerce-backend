package practice.mayank.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import practice.mayank.ecommerce.security.entity.Permission;
import practice.mayank.ecommerce.security.entity.RefreshToken;
import practice.mayank.ecommerce.security.entity.Role;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email", unique = true)
    private String userEmail;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "user_password")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    private String createdByEmail;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(
            name = "users_permission",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<RefreshToken> refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName().getRoleNameWithRolePrefix()));
        authorities.addAll(
                permissions.stream().map(
                        permission -> new SimpleGrantedAuthority(permission.getPermissionName().name())
                ).toList()
        );
        return authorities;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
