package practice.mayank.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Order;
import practice.mayank.ecommerce.entity.OrderStatus;
import practice.mayank.ecommerce.entity.User;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByOrderId(String orderId);

    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    Optional<Order> findByUserAndOrderId(User user, String orderId);

    Page<Order> findByUser(User user, Pageable pageable);
}
