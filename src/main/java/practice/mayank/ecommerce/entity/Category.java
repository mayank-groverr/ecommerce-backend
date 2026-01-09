package practice.mayank.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import practice.mayank.ecommerce.entity.listeners.CategoryListener;
import practice.mayank.ecommerce.repository.ProductRepository;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@EntityListeners(CategoryListener.class)
@Table(name = "categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name" ,nullable = false, unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
    Set<Product> products = new HashSet<>();



}


