package practice.mayank.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
