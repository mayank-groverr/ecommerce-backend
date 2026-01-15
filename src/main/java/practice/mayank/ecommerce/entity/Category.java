package practice.mayank.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name" , unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    Set<Product> products = new HashSet<>();



}


