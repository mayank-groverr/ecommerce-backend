package practice.mayank.ecommerce.dto.order;



import java.util.Set;

public record OrderResponse (
        String orderId,
        Set<OrderItemResponse> orderedItems,

        Double totalPrice
) {
}
