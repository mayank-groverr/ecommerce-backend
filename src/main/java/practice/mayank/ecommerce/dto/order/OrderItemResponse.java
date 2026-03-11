package practice.mayank.ecommerce.dto.order;

import practice.mayank.ecommerce.dto.product.ProductResponse;


public record OrderItemResponse(
        ProductResponse productResponse,
        Double totalPrice,
        Integer orderedQuantity
) {
}
