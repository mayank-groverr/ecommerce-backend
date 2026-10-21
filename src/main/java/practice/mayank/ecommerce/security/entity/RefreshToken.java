package practice.mayank.ecommerce.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import practice.mayank.ecommerce.entity.User;

import java.time.Instant;


@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Instant expiryTime;
}
