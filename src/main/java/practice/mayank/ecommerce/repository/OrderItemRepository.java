package practice.mayank.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.OrderItem;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    List<OrderItem> findByOrderItemId(String orderItemId);
}
