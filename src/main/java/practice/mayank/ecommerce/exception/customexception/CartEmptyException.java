package practice.mayank.ecommerce.exception.customexception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String message){
        super(message);
    }
}
