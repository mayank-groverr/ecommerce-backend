package practice.mayank.ecommerce.exception.model;

import lombok.Getter;
import lombok.Setter;
import practice.mayank.ecommerce.entity.Product;

@Getter
@Setter
public class QuantityViolation {

    private final Product product;
    private final Integer quantityAvailable;
    private final Integer quantityDemanded;
    private final String message;

    public QuantityViolation(
            Product product,
            Integer quantityAvailable,
            Integer quantityDemanded,
            String message
    ){
        this.product = product;
        this.quantityAvailable = quantityAvailable;
        this.quantityDemanded = quantityDemanded;
        this.message = message;
    }
}