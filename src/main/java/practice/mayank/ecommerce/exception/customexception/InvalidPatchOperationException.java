package practice.mayank.ecommerce.exception.customexception;

public class InvalidPatchOperationException extends RuntimeException {
    public InvalidPatchOperationException(String message) {
        super(message);
    }
}
