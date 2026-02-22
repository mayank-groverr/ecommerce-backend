package practice.mayank.ecommerce.exception.customexception;

public class PageSizeExceededException extends RuntimeException {
    public PageSizeExceededException(String message) {
        super(message);
    }
}
