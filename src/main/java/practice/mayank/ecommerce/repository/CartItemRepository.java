package practice.mayank.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Cart;
import practice.mayank.ecommerce.entity.CartItem;
import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, String> {

    Set<CartItem> findByCart(Cart cart);

}
