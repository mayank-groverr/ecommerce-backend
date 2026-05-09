package practice.mayank.ecommerce.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRemoveRequest;
import practice.mayank.ecommerce.dto.cart.CartResponse;
import practice.mayank.ecommerce.dto.cart.cartitem.CartItemRequest;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.service.CartService;
import practice.mayank.ecommerce.service.UserService;
import practice.mayank.ecommerce.validation.groups.OnCreate;
import practice.mayank.ecommerce.validation.groups.OnUpdate;

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
    public ResponseEntity<CartResponse> addToCart(
            @Validated(OnCreate.class) @RequestBody CartItemRequest cartItemRequest
    ){
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
    public ResponseEntity<CartResponse> updateCart(
            @Validated(OnUpdate.class) @RequestBody CartItemRequest cartItemRequest
    ){
        User authenticatedUser = UserService.getAuthenticatedUser();
        CartResponse cartResponse = cartService.updateCart(authenticatedUser, cartItemRequest);
        return ResponseEntity.ok(cartResponse);
    }
}
