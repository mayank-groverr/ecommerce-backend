package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRemoveRequest;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRequest;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemResponse;
import practice.mayank.ecommerce.entity.Cart;
import practice.mayank.ecommerce.entity.CartItem;
import practice.mayank.ecommerce.entity.Product;
import practice.mayank.ecommerce.exception.customexception.CartEmptyException;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.CartItemMapper;
import practice.mayank.ecommerce.repository.CartItemRepository;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;


    public Set<CartItemResponse> getCartItem(Cart cart) {
        Set<CartItem> itemsInCart = findItemsInCart(cart);
        return cartItemMapper.cartItemToCarItemResponses(itemsInCart);
    }

    @Transactional
    public void saveCartItem(CartItemRequest cartItemRequest, Cart cart) {
        Product productById = productService.findProductById(cartItemRequest.productId());
        CartItem cartItem = cartItemMapper.cartItemRequestToCartItem(cartItemRequest);
        Set<CartItem> itemsInCart = findItemsInCart(cart);


        try {
            CartItem itemToBeUpdated = findCartItemInCartByProductId(itemsInCart, productById.getProductId());
            updateCartItemQuantity(itemToBeUpdated, productById, cartItemRequest.quantity());
        } catch (ResourceNotFoundException e) {
            cartItem.setCart(cart);
            cartItem.setProduct(productById);
            cartItem.setTotalPrice(productById.getProductPrice() * cartItemRequest.quantity());
            cartItemRepository.save(cartItem);
        }


    }


    @Transactional
    public void removeCartItem(Cart cart, CartItemRemoveRequest cartItemRemoveRequest) {
        Product productById = productService.findProductById(cartItemRemoveRequest.productId());
        Set<CartItem> itemsInCart = findItemsInCart(cart);
        if (!itemsInCart.isEmpty()) {
            CartItem itemToBeDeleted = findCartItemInCartByProductId(itemsInCart, productById.getProductId());
            cartItemDelete(itemToBeDeleted);
            return;
        }
        throw new CartEmptyException("No item in cart");
    }


    @Transactional
    public void updateCartItem(Cart cart, CartItemRequest cartItemRequest) {
        Product productById = productService.findProductById(cartItemRequest.productId());
        Set<CartItem> itemsInCart = findItemsInCart(cart);
        CartItem itemToBeUpdated = findCartItemInCartByProductId(itemsInCart, productById.getProductId());
        updateCartItemQuantity(itemToBeUpdated, productById, cartItemRequest.quantity());
    }

    private void updateCartItemQuantity(CartItem itemToBeUpdated, Product product, int quantity) {
        if (quantity == 0) {
            cartItemDelete(itemToBeUpdated);
            return;
        }
        itemToBeUpdated.setQuantity(quantity);
        itemToBeUpdated.setTotalPrice(quantity * product.getProductPrice());
    }

    private CartItem findCartItemInCartByProductId(Set<CartItem> cartItems, String productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId().equals(productId)) {
                return cartItem;
            }
        }
        throw new ResourceNotFoundException("No Product found with this id in cart: " + productId);
    }

    public Set<CartItem> findItemsInCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    public void cartItemDelete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
