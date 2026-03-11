package practice.mayank.ecommerce.mapper;


import org.mapstruct.Mapper;
import practice.mayank.ecommerce.dto.order.OrderResponse;
import practice.mayank.ecommerce.entity.Order;



@Mapper(componentModel = "spring",  uses = OrderItemMapper.class)
public interface OrderMapper {

    OrderResponse orderToOrderResponse(Order order);

    Order orderResponseToOrder(OrderResponse orderResponse);
}
