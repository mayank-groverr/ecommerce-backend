package practice.mayank.ecommerce.dto.product;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductSearchRequest(
        @Size(max = 100, message = "Keyword must be at most 100 characters")
        String keyword,

        @Size(max = 100, message = "Category name must be at most 100 characters")
        String categoryName,

        @PositiveOrZero(message = "minPrice cannot be negative")
        Double minPrice,

        @PositiveOrZero(message = "maxPrice cannot be negative")
        Double maxPrice,

        Boolean inStockOnly
) {
    @AssertTrue(message = "minPrice cannot be greater than maxPrice")
    public boolean isPriceRangeValid() {
        return minPrice == null || maxPrice == null || minPrice <= maxPrice;
    }
}