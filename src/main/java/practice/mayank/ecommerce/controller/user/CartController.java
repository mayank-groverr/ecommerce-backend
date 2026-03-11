package practice.mayank.ecommerce.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.cart.CartItemRemoveRequest;
import practice.mayank.ecommerce.dto.cart.CartResponse;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRequest;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.service.CartService;
import practice.mayank.ecommerce.service.UserService;

@RestController
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @GetMapping("/get")
    public ResponseEntity<CartResponse> getCart(){
        User authenticatedUser = UserService.getAuthenticatedUser();
        CartResponse cartResponse = cartService.getCart(authenticatedUser);
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartItemRequest cartItemRequest){
        User authenticatedUser = UserService.getAuthenticatedUser();
        CartResponse cartResponse = cartService.addToCart(authenticatedUser, cartItemRequest);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping("/remove-from-cart")
    public ResponseEntity<CartResponse> removeFromCart(@Valid @RequestBody CartItemRemoveRequest cartItemRemoveRequest){
        User authenticatedUser = UserService.getAuthenticatedUser();
        CartResponse cartResponse = cartService.removeFromCart(authenticatedUser, cartItemRemoveRequest);
        return ResponseEntity.ok(cartResponse);
    }

    @PatchMapping("/update-cart")
    public ResponseEntity<CartResponse> updateCart(@Valid @RequestBody CartItemRequest cartItemRequest){
        User authenticatedUser = UserService.getAuthenticatedUser();
        CartResponse cartResponse = cartService.updateCart(authenticatedUser, cartItemRequest);
        return ResponseEntity.ok(cartResponse);
    }
}
