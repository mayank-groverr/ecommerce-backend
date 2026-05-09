package practice.mayank.ecommerce.controller.admin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.order.OrderResponse;
import practice.mayank.ecommerce.dto.order.OrderStatusRequest;
import practice.mayank.ecommerce.entity.OrderStatus;
import practice.mayank.ecommerce.service.OrderService;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/orders")
@Validated
public class OrderManagementController {

    private final OrderService orderService;

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderResponse> getAllPlacedOrders(@PathVariable String id) {
        OrderResponse ordersById = orderService.getOrdersById(id);
        return ResponseEntity.ok(ordersById);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<OrderResponse>> getAllPlacedOrders(

            @PositiveOrZero
            @RequestParam(name = "pn")
            int pageNumber,

            @Positive
            @RequestParam(name = "ps")
            @Max(20)
            int pageSize

    ) {
        Page<OrderResponse> allPlacedOrders = orderService.getAllPlacedOrders(pageNumber, pageSize);
        return ResponseEntity.ok(allPlacedOrders);
    }

    @GetMapping("/get-all-by-status")
    public ResponseEntity<Set<OrderResponse>> getOrdersByStatus(
            @RequestParam(name = "os")
            OrderStatus orderStatus,

            @PositiveOrZero
            @RequestParam(name = "pn")
            int pageNumber,

            @Positive
            @RequestParam(name = "ps")
            @Max(20)
            int pageSize
    ) {
        Set<OrderResponse> ordersByStatus = orderService.getOrdersByStatus(orderStatus, pageNumber, pageSize);
        return ResponseEntity.ok(ordersByStatus);
    }

    @PatchMapping("/update-status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @RequestBody OrderStatusRequest orderStatusRequest,
            @RequestParam(name = "id") String orderId
    ) {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId, orderStatusRequest);
        return ResponseEntity.ok(orderResponse);
    }
}
