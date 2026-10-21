package practice.mayank.ecommerce.entity;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import practice.mayank.ecommerce.dto.product.ProductSearchRequest;
import practice.mayank.ecommerce.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    private ProductSpecification() {}

    public static Specification<Product> from(ProductSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.keyword() != null && !request.keyword().isBlank()) {
                String pattern = "%" +
                        escapeLike(request.keyword().trim().toLowerCase()) +
                        "%";

                predicates.add(
                        cb.or(
                                cb.like(
                                        cb.lower(root.<String>get("productName")),
                                        pattern,
                                        '\\'
                                ),
                                cb.like(
                                        cb.lower(root.<String>get("productDescription")),
                                        pattern,
                                        '\\'
                                )
                        )
                );
            }
            if (request.categoryName() != null && !request.categoryName().isBlank()) {
                predicates.add(cb.equal(
                        cb.lower(root.get("category").<String>get("categoryName")),
                        request.categoryName().trim().toLowerCase()));
            }
            if (request.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.<Double>get("productPrice"), request.minPrice()));
            }
            if (request.maxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.<Double>get("productPrice"), request.maxPrice()));
            }
            if (Boolean.TRUE.equals(request.inStockOnly())) {
                predicates.add(cb.greaterThan(root.<Integer>get("productStock"), 0));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Prevents users from injecting % and _ wildcards
    private static String escapeLike(String s) {
        return s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}