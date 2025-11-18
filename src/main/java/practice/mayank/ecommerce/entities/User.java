package practice.mayank.ecommerce.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("users")
public class User {
    private String userName;
    @Id
    private String email;
    private String mobileNumber;
    private String password;
}
