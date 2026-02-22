package practice.mayank.ecommerce.mapper;

import org.mapstruct.*;
import practice.mayank.ecommerce.dto.product.ProductRequest;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "categoryDto")
    Product productRequestToProduct(ProductRequest productRequest);

    @Mapping(target = "category", ignore = true)
    Product productRequestToProductWithoutCategory(ProductRequest productRequest);

    @Mapping(target = "categoryDto", source = "category")
    ProductRequest productToProductRequest(Product product);

    @Mapping(target = "categoryDto", source = "category")
    ProductResponse productToProductResponse(Product product);

    @Mapping(target = "category", ignore = true)
    void updateExistingProduct(ProductRequest productRequest, @MappingTarget Product product);

}
