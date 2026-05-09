package practice.mayank.ecommerce.validation.handler;

import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomValidationHandler {

    private final Validator validator;


    public <T> void validate(T valueToBeValidated, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(valueToBeValidated, groups);
        ifEmptyThrowException(violations);
    }

    public <T> void validate(T valueToBeValidated) {
        Set<ConstraintViolation<T>> violations = validator.validate(valueToBeValidated);
        ifEmptyThrowException(violations);
    }

    public <T> void ifEmptyThrowException(Set<ConstraintViolation<T>> violations) {
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }



}

