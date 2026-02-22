package practice.mayank.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private  String productDescription;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "product_Stock")
    private Integer productStock;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category  category;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

}
