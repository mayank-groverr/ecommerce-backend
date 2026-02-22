package practice.mayank.ecommerce.validation.handler;

import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomValidationHandler {

    private final Validator validator;

    public CustomValidationHandler(Validator validator){
        this.validator = validator;
    }

    public <T> void validate(T valueToBeValidated, Class<?>... groups){
        Set<ConstraintViolation<T>> violations = validator.validate(valueToBeValidated, groups);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

}
