package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRemoveRequest;
import practice.mayank.ecommerce.dto.cart.CartResponse;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRequest;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemResponse;
import practice.mayank.ecommerce.entity.Cart;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.repository.CartRepository;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;



    @Transactional
    public void createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }


    public CartResponse getCart(User user) {
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(user.getCart());
        return new CartResponse(itemsInCart);
    }


    public CartResponse addToCart(User user, CartItemRequest cartItemRequest) {
        Cart cart = user.getCart();
        cartItemService.saveCartItem(cartItemRequest, cart);
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(cart);
        return new CartResponse(itemsInCart);
    }


    public CartResponse removeFromCart(User user, CartItemRemoveRequest cartItemRemoveRequest) {
        Cart cart = user.getCart();
        cartItemService.removeCartItem(cart, cartItemRemoveRequest);
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(cart);
        return new CartResponse(itemsInCart);
    }

    public CartResponse updateCart(User user, CartItemRequest cartItemRequest) {
        Cart cart = user.getCart();
        cartItemService.updateCartItem(cart, cartItemRequest);
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(cart);
        return new CartResponse(itemsInCart);
    }


}
