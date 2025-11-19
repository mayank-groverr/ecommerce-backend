package practice.mayank.ecommerce.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("users")
public class User {
    private String name;
    @Id
    private String email;
    private String mobileNumber;
    private String password;
    private List<String> roles = new ArrayList<>();
}
