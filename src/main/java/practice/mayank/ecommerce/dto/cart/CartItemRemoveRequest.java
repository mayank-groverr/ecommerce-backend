package practice.mayank.ecommerce.dto.cart;

import jakarta.validation.constraints.NotBlank;

public record CartItemRemoveRequest(
        @NotBlank(message = "Product id can't be blank")
        String productId
) {
}
