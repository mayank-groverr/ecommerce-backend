package practice.mayank.ecommerce.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.mayank.ecommerce.dto.cart.CartResponse;
import practice.mayank.ecommerce.entity.Cart;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper{


    CartResponse cartToCartResponse(Cart cart);

}
