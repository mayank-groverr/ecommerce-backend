package practice.mayank.ecommerce.dto.security;


import java.util.Date;

public record SecurityTokenResponse(
        String jwtToken,
        Date expiryTime
) {
}
