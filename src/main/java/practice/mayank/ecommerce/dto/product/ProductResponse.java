package practice.mayank.ecommerce.dto.product;


import practice.mayank.ecommerce.dto.CategoryDto;

public record ProductResponse(
        String productId,
        String productName,
        String productDescription,
        double productPrice,
        int productStock,
        String imageUrl,
        CategoryDto categoryDto
){
}
