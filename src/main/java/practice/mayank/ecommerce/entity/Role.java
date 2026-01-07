package practice.mayank.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private String id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;
    
}
