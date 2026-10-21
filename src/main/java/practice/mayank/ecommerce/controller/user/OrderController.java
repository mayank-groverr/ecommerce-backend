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
import practice.mayank.ecommerce.service.OrderService;
import practice.mayank.ecommerce.service.UserService;

@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/get")
    public ResponseEntity<OrderResponse> getOrderById(@RequestParam(name = "id") String orderId){
        OrderResponse ordersById = orderService.getUserOrderById(orderId);
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
        Page<OrderResponse> allOrders = orderService.getAllUserOrders(pageNumber, pageSize);
        return ResponseEntity.ok(allOrders);
    }

    @PostMapping("/place-order")
    public ResponseEntity<OrderResponse> placeOrder(){
        OrderResponse orderResponse = orderService.placeOrder();
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@RequestParam(name = "id") String orderId){
        orderService.cancelUserOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
