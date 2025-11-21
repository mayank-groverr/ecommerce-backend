package practice.mayank.ecommerce.dtos.request;
import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String mobileNumber;
    private String password;
}
