package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.order.OrderResponse;
import practice.mayank.ecommerce.dto.order.OrderStatusRequest;
import practice.mayank.ecommerce.entity.*;
import practice.mayank.ecommerce.exception.customexception.AlreadyCancelledException;
import practice.mayank.ecommerce.exception.customexception.NoOrdersFoundException;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.OrderMapper;
import practice.mayank.ecommerce.repository.OrderRepository;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.security.services.AuthenticatedUserProvider;
import practice.mayank.ecommerce.security.services.PermissionService;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final AuthenticatedUserProvider authenticatedUserProvider;




    public OrderResponse getUserOrderById(String orderId) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Order placedOrder = findspecificUserOrder(authenticatedUser, orderId);
        return orderMapper.orderToOrderResponse(placedOrder);
    }


    public OrderResponse getOrderById(String orderId) {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.ORDER_MANAGEMENT);
        Order orderById = findOrderById(orderId);
        return orderMapper.orderToOrderResponse(orderById);
    }


    public Page<OrderResponse> getAllUserOrders(int pageNumber, int pageSize) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Page<Order> userOrders = orderRepository.findByUser(authenticatedUser, PageRequest.of(pageNumber, pageSize));
        return userOrders.map(orderMapper::orderToOrderResponse);
    }


    public Page<OrderResponse> getAllPlacedOrders(int pageNumber, int pageSize) {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.ORDER_MANAGEMENT);
        Page<Order> allPlacedOrder = orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return allPlacedOrder.map(orderMapper::orderToOrderResponse);
    }


    public Page<OrderResponse> getOrdersByStatus(OrderStatus orderStatus, int pageNumber, int pageSize) {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.ORDER_MANAGEMENT);
        Page<Order> orderByStatus = findOrderByStatus(orderStatus, pageNumber, pageSize);
        return orderByStatus.map(orderMapper::orderToOrderResponse);
    }


    @Transactional
    public OrderResponse placeOrder() {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Order unplacedOrder = new Order();
        unplacedOrder.setUser(authenticatedUser);
        unplacedOrder.setOrderStatus(OrderStatus.PROCESSING);
        unplacedOrder.setOrderPlacedAt(new Date());
        Order placedOrder = orderRepository.save(unplacedOrder);
        Set<OrderItem> itemsOrdered = orderItemService.createOrderItem(authenticatedUser.getCart(), placedOrder);
        placedOrder.setOrderedItems(itemsOrdered);
        placedOrder.setTotalPrice(orderItemService.getTotalPriceOfOrderItems(itemsOrdered));
        return orderMapper.orderToOrderResponse(placedOrder);
    }



    @Transactional
    public OrderResponse updateOrderStatus(String orderId, OrderStatusRequest orderStatusRequest)  {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.ORDER_MANAGEMENT);
        OrderStatus orderStatus = orderStatusRequest.orderStatus();
        if(orderStatus == OrderStatus.CANCELLED){
            throw new IllegalArgumentException("You cannot mark a order as cancelled");
        }
        Order orderById = findOrderById(orderId);
        if (orderById.getOrderStatus() == OrderStatus.CANCELLED){
            throw new AlreadyCancelledException("Order cancelled by user");
        }

        orderById.setOrderStatus(orderStatus);

        return orderMapper.orderToOrderResponse(orderById);
    }

    @Transactional
    public void cancelUserOrder(String orderId) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        Order order = findspecificUserOrder(authenticatedUser, orderId);
        if(order.getOrderStatus() == OrderStatus.CANCELLED){
            throw new AlreadyCancelledException("Order already cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
    }

    private Order searchOrderById(Set<Order> orders, String orderId) {
        for(Order order : orders){
            if(order.getOrderId().equals(orderId)){
                return order;
            }
        }
        throw new ResourceNotFoundException("No order found with id:" + orderId);
    }

    private Page<Order> findOrderByStatus(OrderStatus orderStatus, int pageNumber, int pageSize) {
        return orderRepository.findByOrderStatus(orderStatus, PageRequest.of(pageNumber, pageSize));
    }

    private Order findOrderById(String orderId){
        Optional<Order> orderById = orderRepository.findByOrderId(orderId);
        return orderById.orElseThrow(() -> new ResourceNotFoundException("No order found with this id: " + orderId));
    }

    private Order findspecificUserOrder(User user, String orderId){
        Optional<Order> orderByUserAndId = orderRepository.findByUserAndOrderId(user, orderId);
        return orderByUserAndId.orElseThrow(
                () -> new  NoOrdersFoundException("No orders found with this orderID: " + orderId));
    }

}
