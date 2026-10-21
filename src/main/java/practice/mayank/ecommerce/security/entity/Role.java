package practice.mayank.ecommerce.security.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "roles")
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class Role {


    public Role(RoleEnum roleName) {
        this.roleName = roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    @EqualsAndHashCode.Exclude
    private String id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;
    
}
