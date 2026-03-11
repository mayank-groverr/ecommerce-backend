package practice.mayank.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Order;
import practice.mayank.ecommerce.entity.OrderStatus;
import practice.mayank.ecommerce.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByOrderId(String orderId);

    List<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    Set<Order> findByUser(User user);

    Page<Order> findByUser(User user, Pageable pageable);
}
