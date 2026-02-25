package practice.mayank.ecommerce.dto.product;



import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import practice.mayank.ecommerce.dto.category.CategoryRequest;



public record ProductRequest(

        @NotBlank(message = "Product name is required")
        String productName,

        @Size(max = 700)
        String productDescription,

        @Positive(message = "Price must be more than zero")
        @NotNull(message = "Price is required")
        Double productPrice,

        @Positive(message = "Stock must be more than zero")
        @NotNull(message = "Stock is required")
        Integer productStock,

        @NotBlank(message = "Image url cannot be Blank")
        String imageUrl,

        @Valid
        CategoryRequest categoryRequest

) {
}
