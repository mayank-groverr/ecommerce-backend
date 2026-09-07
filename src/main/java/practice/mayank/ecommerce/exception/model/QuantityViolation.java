package practice.mayank.ecommerce.exception.model;

import lombok.Getter;
import lombok.Setter;
import practice.mayank.ecommerce.dto.product.ProductRequest;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.entity.Product;


public record QuantityViolation
        (
                ProductResponse product,
                Integer quantityAvailable,
                Integer quantityDemanded,
                String message
        ) {

}