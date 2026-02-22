package practice.mayank.ecommerce.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(

        @NotBlank(message = "Category name cannot be blank")
        @Size(max = 100, message = "Category name must be between 1 and 100 characters")
        String categoryName

) {
}
