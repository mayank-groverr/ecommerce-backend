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
import practice.mayank.ecommerce.security.services.AuthenticatedUserProvider;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final AuthenticatedUserProvider authenticatedUserProvider;



    @Transactional
    public void createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }


    public CartResponse getCart() {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(authenticatedUser.getCart());
        return new CartResponse(itemsInCart);
    }


    public CartResponse addToCart(CartItemRequest cartItemRequest) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Cart cart = authenticatedUser.getCart();
        cartItemService.saveCartItem(cartItemRequest, cart);
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(cart);
        return new CartResponse(itemsInCart);
    }


    public CartResponse removeFromCart(CartItemRemoveRequest cartItemRemoveRequest) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Cart cart = authenticatedUser.getCart();
        cartItemService.removeCartItem(cart, cartItemRemoveRequest);
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(cart);
        return new CartResponse(itemsInCart);
    }

    public CartResponse updateCart(CartItemRequest cartItemRequest) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Cart cart = authenticatedUser.getCart();
        cartItemService.updateCartItem(cart, cartItemRequest);
        Set<CartItemResponse> itemsInCart = cartItemService.getCartItem(cart);
        return new CartResponse(itemsInCart);
    }


}
