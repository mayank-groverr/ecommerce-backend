package practice.mayank.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;
import practice.mayank.ecommerce.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
