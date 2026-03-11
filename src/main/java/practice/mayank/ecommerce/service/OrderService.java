package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.order.OrderResponse;
import practice.mayank.ecommerce.dto.order.OrderStatusRequest;
import practice.mayank.ecommerce.entity.Order;
import practice.mayank.ecommerce.entity.OrderItem;
import practice.mayank.ecommerce.entity.OrderStatus;
import practice.mayank.ecommerce.entity.User;
import practice.mayank.ecommerce.exception.customexception.NoOrdersFoundException;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.OrderMapper;
import practice.mayank.ecommerce.repository.OrderRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;




    public OrderResponse getOrdersById(User user, String orderId) {
        Set<Order> placedOrders = orderRepository.findByUser(user);
        if(!placedOrders.isEmpty()){
            Order order = searchOrderById(placedOrders, orderId);
            return orderMapper.orderToOrderResponse(order);
        }
        throw new NoOrdersFoundException("No orders yet");
    }


    public Page<OrderResponse> getAllOrders(User user, int pageNumber, int pageSize) {
        Page<Order> userOrders = orderRepository.findByUser(user, PageRequest.of(pageNumber, pageSize));
        return userOrders.map(orderMapper::orderToOrderResponse);
    }

    public Page<OrderResponse> getAllPlacedOrders(int pageNumber, int pageSize) {
        Page<Order> allPlacedOrder = orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return allPlacedOrder.map(orderMapper::orderToOrderResponse);
    }

    public Set<OrderResponse> getOrdersByStatus(OrderStatus orderStatus, int pageNumber, int pageSize) {
        List<Order> orderByStatus = findOrderByStatus(orderStatus, pageNumber, pageSize);
        return orderByStatus.stream().map(orderMapper::orderToOrderResponse).collect(Collectors.toSet());
    }


    @Transactional
    public OrderResponse placeOrder(User user) {
        Order unplacedOrder = new Order();
        unplacedOrder.setUser(user);
        unplacedOrder.setOrderStatus(OrderStatus.PROCESSING);
        unplacedOrder.setOrderPlacedAt(new Date());
        Order placedOrder = orderRepository.save(unplacedOrder);
        Set<OrderItem> itemsOrdered = orderItemService.createOrderItem(user.getCart(), placedOrder);
        unplacedOrder.setTotalPrice(orderItemService.getTotalPriceOfOrderItems(itemsOrdered));
        unplacedOrder.setOrderedItems(itemsOrdered);
        return orderMapper.orderToOrderResponse(placedOrder);
    }


    @Transactional
    public OrderResponse updateOrderStatus(String orderId, OrderStatusRequest orderStatusRequest)  {
        OrderStatus orderStatus = orderStatusRequest.orderStatus();
        if(orderStatus == OrderStatus.CANCELLED){
            throw new IllegalArgumentException("You cannot mark a order as cancelled");
        }
        Order orderById = findOrderById(orderId);
        orderById.setOrderStatus(orderStatus);

        return orderMapper.orderToOrderResponse(orderById);
    }

    @Transactional
    public void cancelOrder(User user, String orderId) {
        Set<Order> userOrders = orderRepository.findByUser(user);
        Order order = searchOrderById(userOrders, orderId);
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

    private List<Order> findOrderByStatus(OrderStatus orderStatus, int pageNumber, int pageSize) {
        return orderRepository.findByOrderStatus(orderStatus, PageRequest.of(pageNumber, pageSize));
    }

    public Order findOrderById(String orderId){
        Optional<Order> orderById = orderRepository.findByOrderId(orderId);
        return orderById.orElseThrow(() -> new ResourceNotFoundException("No order found with this id: " + orderId));
    }

}
