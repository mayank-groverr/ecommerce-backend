package practice.mayank.ecommerce.dto.order;



import practice.mayank.ecommerce.entity.OrderStatus;

import java.util.Set;

public record OrderResponse (
        String orderId,
        Double totalPrice,
        OrderStatus orderStatus,
        Set<OrderItemResponse> orderedItems

) {
}
