package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.entity.*;
import practice.mayank.ecommerce.exception.customexception.CartEmptyException;
import practice.mayank.ecommerce.exception.customexception.QuantityViolationException;
import practice.mayank.ecommerce.exception.model.QuantityViolation;
import practice.mayank.ecommerce.mapper.OrderItemMapper;
import practice.mayank.ecommerce.repository.OrderItemRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final CartItemService cartItemService;

    @Transactional
    public Set<OrderItem> createOrderItem(Cart cart, Order order){
        Set<QuantityViolation> quantityViolations = new HashSet<>();
        Set<OrderItem> unOrderedItems = new HashSet<>();
        Set<CartItem> itemsInCart = cartItemService.findItemsInCart(cart);

        if(!itemsInCart.isEmpty()){
            itemsInCart.forEach(cartItem -> {

                QuantityViolation isProductInStock = isProductInStock(cartItem.getProduct(), cartItem.getQuantity());

                if(isProductInStock != null){
                    quantityViolations.add(isProductInStock);
                }else{
                    OrderItem orderItem = orderItemMapper.cartItemToOrderItem(cartItem);
                    orderItem.setOrder(order);
                    unOrderedItems.add(orderItem);
                }

            });

            if(!quantityViolations.isEmpty()){
                throw new QuantityViolationException(quantityViolations);
            }

            List<OrderItem> placedOrderItems = orderItemRepository.saveAll(unOrderedItems);
            return new HashSet<>(placedOrderItems) ;
        }

        throw new CartEmptyException("Oops! Cart is Empty");


    }

    public Double getTotalPriceOfOrderItems(Set<OrderItem> orderItems){
        Double totalPrice = 0d;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    private QuantityViolation isProductInStock(Product product, Integer orderedQuantity){
        Integer productStock = product.getProductStock();
        QuantityViolation quantityViolation;
        if(productStock == 0) {
            quantityViolation =
                    new QuantityViolation(
                            product,
                            product.getProductStock(),
                            0,
                            "Product not in stock"
                    );
            return quantityViolation;
        }else if(orderedQuantity > productStock){
            quantityViolation =
                    new QuantityViolation(
                                    product,
                                    product.getProductStock(),
                                    orderedQuantity,
                                    String.format("Product not available in such quantity: %d available quantity: %d",
                                            orderedQuantity, productStock)
                    );
            return quantityViolation;
        }
        return null;
    }






}
