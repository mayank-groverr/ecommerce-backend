package practice.mayank.ecommerce.dto;

import java.util.HashSet;


public record CartDto (
        HashSet<CartItemDto> cartItems
){
}
