package practice.mayank.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
}
