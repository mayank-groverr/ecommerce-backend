package practice.mayank.ecommerce.service;

import jakarta.persistence.SecondaryTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.dto.CartDto;
import practice.mayank.ecommerce.dto.CartItemDto;
import practice.mayank.ecommerce.entity.Cart;
import practice.mayank.ecommerce.entity.CartItem;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.mapper.GenericMapper;
import practice.mayank.ecommerce.repository.CartRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final GenericMapper genericMapper;
    private final CartItemService cartItemService;


    public void createNewCart(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    public CartDto getCart(User user){
        Cart cart = user.getCart();
        HashSet<CartItemDto> cartItemDtos = new HashSet<>();
        for (CartItem cartItem : cart.getCartItems()) {
            cartItemDtos.add(genericMapper.cartItemToCartItemDto(cartItem));
        }
        return new CartDto(cartItemDtos);
    }


    public boolean addToCart(User user, CartItemDto cartItemDto){
        try{
            Cart cart = user.getCart();
            cartItemService.saveCartItem(cartItemDto,cart);
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }


    public boolean removeFromCart(User user, CartItemDto cartItemDto){
        try{
            Cart cart = user.getCart();
            cartItemService.removeCartItem(cart, cartItemDto.productId());
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    public boolean updateCart(User user, CartItemDto cartItemDto){
        try{
            cartItemService.updateCartItem(user.getCart(), cartItemDto);
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }




}
