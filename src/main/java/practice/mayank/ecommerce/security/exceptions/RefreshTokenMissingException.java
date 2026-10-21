package practice.mayank.ecommerce.security.exceptions;

public class RefreshTokenMissingException extends RuntimeException {
    public RefreshTokenMissingException(String message) {
        super(message);
    }
}
