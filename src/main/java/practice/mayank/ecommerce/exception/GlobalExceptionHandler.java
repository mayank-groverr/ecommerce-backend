package practice.mayank.ecommerce.exception;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import practice.mayank.ecommerce.exception.customexception.InvalidPatchOperationException;
import practice.mayank.ecommerce.exception.customexception.QuantityViolationException;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = ErrorResponseUtil.of(ex.getMessage(), "Resource not found", HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {

        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        ProblemDetail problemDetail = ErrorResponseUtil.of("Validation Failed", HttpStatus.BAD_REQUEST, request);
        problemDetail.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problemDetail);
    }


    @ExceptionHandler
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request
    ) {

        HashMap<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(
                constraintViolation -> errors.put(constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getMessage()));

        ProblemDetail problemDetail = ErrorResponseUtil.of("Validation Failed", HttpStatus.BAD_REQUEST, request);
        problemDetail.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleInvalidPatchOperation(InvalidPatchOperationException ex, WebRequest request) {
        ProblemDetail problemDetail = ErrorResponseUtil.of(ex.getMessage(), "Update Failed", HttpStatus.BAD_REQUEST, request);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleQuantityViolationException(QuantityViolationException ex, WebRequest request) {
        ProblemDetail orderCannotBeServed = ErrorResponseUtil.of("Order cannot be served", HttpStatus.BAD_REQUEST, request);
        orderCannotBeServed.setProperty("errors",ex.getQuantityViolations());
        return ResponseEntity.badRequest().body(orderCannotBeServed);
    }
}
