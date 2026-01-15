package practice.mayank.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {
}
