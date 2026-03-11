package practice.mayank.ecommerce.dto.order;

import jakarta.validation.constraints.NotBlank;
import practice.mayank.ecommerce.entity.OrderStatus;

public record OrderStatusRequest(
        @NotBlank(message = "Order Status cannot be blank")
        OrderStatus orderStatus
) {
}
