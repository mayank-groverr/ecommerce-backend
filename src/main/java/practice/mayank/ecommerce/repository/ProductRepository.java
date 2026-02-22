package practice.mayank.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.entity.Product;


public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByCategory(Category category, Pageable pageable);

    Page<Product> findByCategoryIsNull(Pageable pageable);
}
