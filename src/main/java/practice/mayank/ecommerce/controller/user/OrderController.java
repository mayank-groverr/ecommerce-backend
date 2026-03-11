package practice.mayank.ecommerce.controller.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.order.OrderResponse;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.service.OrderItemService;
import practice.mayank.ecommerce.service.OrderService;
import practice.mayank.ecommerce.service.UserService;
import java.util.Set;

@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/get")
    public ResponseEntity<OrderResponse> getOrderById(@RequestParam(name = "id") String orderId){
        User authenticatedUser = UserService.getAuthenticatedUser();
        OrderResponse ordersById = orderService.getOrdersById(authenticatedUser, orderId);
        return ResponseEntity.ok(ordersById);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @PositiveOrZero
            @RequestParam(name = "pn")
            int pageNumber,

            @Positive
            @RequestParam(name = "ps")
            @Max(20)
            int pageSize
    ){
        User authenticatedUser = UserService.getAuthenticatedUser();
        Page<OrderResponse> allOrders = orderService.getAllOrders(authenticatedUser, pageNumber, pageSize);
        return ResponseEntity.ok(allOrders);
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderResponse> placeOrder(){
        User user = UserService.getAuthenticatedUser();
        OrderResponse orderResponse = orderService.placeOrder(user);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@RequestParam(name = "id") String orderId){
        User user = UserService.getAuthenticatedUser();
        orderService.cancelOrder(user, orderId);
        return ResponseEntity.noContent().build();
    }

}
