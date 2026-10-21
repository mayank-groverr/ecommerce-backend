package practice.mayank.ecommerce.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Permission {

    public Permission(PermissionEnum permissionName) {
        this.permissionName = permissionName;
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @EqualsAndHashCode.Exclude
    private String id;


    @Enumerated(EnumType.STRING)
    private PermissionEnum permissionName;

}
