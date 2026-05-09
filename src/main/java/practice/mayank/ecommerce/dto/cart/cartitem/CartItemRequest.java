package practice.mayank.ecommerce.dto.cart.cartitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import practice.mayank.ecommerce.validation.groups.OnCreate;
import practice.mayank.ecommerce.validation.groups.OnUpdate;

public record CartItemRequest(
        @NotBlank(message = "Product id can't be blank")
        String productId,
        @Positive(message = "Product quantity should be more than zero", groups = OnCreate.class)
        @PositiveOrZero(message = "Product quantity should be 0 or  more than zero", groups = OnUpdate.class)
        int quantity
){
}
