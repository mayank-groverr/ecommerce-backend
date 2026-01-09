package practice.mayank.ecommerce.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.mayank.ecommerce.entity.Category;


import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByCategoryName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.categoryName = :newName WHERE c.categoryName = :oldName")
    int updateCategoryByName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Transactional
    void deleteByCategoryName(String name);
}
