package practice.mayank.ecommerce.security.dto;

import java.util.Date;

public record SecurityTokenResponse(
        String jwt,
        Date expiry
) {
}
