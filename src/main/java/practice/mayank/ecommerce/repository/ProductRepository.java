package practice.mayank.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.entity.Product;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByCategory(Category category);
}
