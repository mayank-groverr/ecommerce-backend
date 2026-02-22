package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.dto.CartItemDto;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.entity.Cart;
import practice.mayank.ecommerce.entity.CartItem;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.GenericMapper;
import practice.mayank.ecommerce.repository.CartItemRepository;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CartItemService {

    private final GenericMapper genericMapper;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;


    public void saveCartItem(CartItemDto cartItemDto, Cart cart) {
        ProductResponse productById = productService.getProductById(cartItemDto.productId());
        CartItem cartItem = genericMapper.cartItemDtoToCartItem(cartItemDto);
        if (cartItem.getQuantity() <= productById.productStock()) {
            if (itemAlreadyInCart(cart.getCartItems(), productById.productId())) {
                updateCartItem(cart, new CartItemDto(productById.productId(), 1));
                return;
            }
            cartItem.setCart(cart);
            cartItem.setProduct(genericMapper.productDtoToProduct(productById));
            cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("Stock not available currently");
        }
    }

    public void removeCartItem(Cart cart, String productId) {
        productService.getProductById(productId);
        CartItem itemToBeDeleted = findCartItemByProductId(cart.getCartItems(), productId);
        if (itemToBeDeleted != null) {
            cartItemRepository.delete(itemToBeDeleted);
        }
        throw new ResourceNotFoundException("Item not found in cart");
    }

    public void updateCartItem(Cart cart, CartItemDto cartItemDto) {
        ProductResponse productInDb = productService.getProductById(cartItemDto.productId());
        CartItem itemToBeUpdated = findCartItemByProductId(cart.getCartItems(), productInDb.productId());
        if (itemToBeUpdated != null && cartItemDto.quantity() <= productInDb.productStock()) {
            itemToBeUpdated.setQuantity(cartItemDto.quantity() + itemToBeUpdated.getQuantity());
            cartItemRepository.save(itemToBeUpdated);
        }
        throw new ResourceNotFoundException("Item not found in cart");
    }

    private boolean itemAlreadyInCart(Set<CartItem> cartItems, String productId) {
        return cartItems.stream().anyMatch(cartItem -> cartItem.getProduct().getProductId().equals(productId));
    }

    private CartItem findCartItemByProductId(Set<CartItem> cartItems, String productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId().equals(productId)) {
                return cartItem;
            }
        }
        return null;
    }
}
