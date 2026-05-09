package practice.mayank.ecommerce.exception.customexception;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import practice.mayank.ecommerce.exception.model.QuantityViolation;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class QuantityViolationException extends RuntimeException {

    private Set<QuantityViolation> quantityViolations;

    public QuantityViolationException(Set<QuantityViolation> quantityViolations){
        this.quantityViolations = quantityViolations;
    }


}
