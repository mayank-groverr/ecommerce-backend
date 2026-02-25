package practice.mayank.ecommerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

        @NotBlank(message = "Category name cannot be blank")
        @Size(min = 1, max = 100, message = "Category name length must be between 1 and 100")
        String categoryName

){
}
