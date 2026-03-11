package practice.mayank.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.mayank.ecommerce.dto.order.OrderItemResponse;
import practice.mayank.ecommerce.entity.CartItem;
import practice.mayank.ecommerce.entity.OrderItem;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderItemMapper {

    OrderItem orderItemResponseToOrderItem(OrderItemResponse orderItemResponse);

    @Mapping(target="productResponse", source = "productOrdered")
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);

    @Mapping(target = "productOrdered", source = "product")
    @Mapping(target = "orderedQuantity", source = "quantity")
    OrderItem cartItemToOrderItem(CartItem cartItem);

}
