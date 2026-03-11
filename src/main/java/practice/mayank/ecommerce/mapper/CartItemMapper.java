package practice.mayank.ecommerce.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRequest;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemResponse;
import practice.mayank.ecommerce.entity.CartItem;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem cartItemRequestToCartItem(CartItemRequest cartItemRequest);

    @Mapping(target = "productId", source = "product.productId")
    CartItemResponse cartItemToCartItemResponse(CartItem cartItem);
    Set<CartItemResponse> cartItemToCarItemResponses(Set<CartItem> cartItems);
}
