package practice.mayank.ecommerce.dto.product;



import practice.mayank.ecommerce.dto.category.CategoryResponse;

public record ProductResponse(
        String productId,
        String productName,
        String productDescription,
        double productPrice,
        int productStock,
        String imageUrl,
        CategoryResponse categoryResponse
){
}
