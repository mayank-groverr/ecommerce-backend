package practice.mayank.ecommerce.dto.cart;

import practice.mayank.ecommerce.dto.cart.cartitem.CartItemResponse;

import java.util.Set;

public record CartResponse(
        Set<CartItemResponse> cartItems
){
}