package practice.mayank.ecommerce.dto;



public record ProductDto (
        String productId,
        String productName,
        String productDescription,
        double productPrice,
        int productStock,
        String imageUrl,

        CategoryDto categoryDto
){
}
