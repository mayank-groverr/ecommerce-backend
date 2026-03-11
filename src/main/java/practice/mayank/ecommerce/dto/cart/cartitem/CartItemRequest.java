package practice.mayank.ecommerce.dto.cart.cartitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CartItemRequest(
        @NotBlank(message = "Product id can't be blank")
        String productId,
        @PositiveOrZero(message = "Product quantity should be 0 or more than zero")
        int quantity
){
}
