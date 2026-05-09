package practice.mayank.ecommerce.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.mayank.ecommerce.entity.Category;


import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByCategoryName(String categoryName);

    @Transactional
    void deleteByCategoryName(String name);
}
