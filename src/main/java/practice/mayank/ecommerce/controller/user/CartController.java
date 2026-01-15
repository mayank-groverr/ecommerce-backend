package practice.mayank.ecommerce.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.CartDto;
import practice.mayank.ecommerce.dto.CartItemDto;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.service.CartService;

@RestController
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @GetMapping("/get-cart")
    public ResponseEntity<CartDto> getCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CartDto cart = cartService.getCart((User) authentication.getPrincipal());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDto cartItemDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(cartService.addToCart((User) authentication.getPrincipal(), cartItemDto)){
            return new ResponseEntity<>("Added to Cart",HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/remove-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestBody CartItemDto cartItemDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(cartService.removeFromCart((User) authentication.getPrincipal(), cartItemDto)){
            return new ResponseEntity<>("Removed From Cart",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/update-cart")
    public ResponseEntity<String> udpateCart(@RequestBody CartItemDto cartItemDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(cartService.updateCart((User) authentication.getPrincipal(), cartItemDto)){
            return new ResponseEntity<>("Cart Successfully Updated",HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
