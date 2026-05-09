package practice.mayank.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date orderPlacedAt;

    private Double totalPrice;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderedItems;
}
