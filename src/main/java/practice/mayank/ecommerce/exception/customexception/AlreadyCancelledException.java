package practice.mayank.ecommerce.exception.customexception;

public class AlreadyCancelledException extends RuntimeException {
    public AlreadyCancelledException(String orderAlreadyCancelled) {
        super(orderAlreadyCancelled);
    }
}
