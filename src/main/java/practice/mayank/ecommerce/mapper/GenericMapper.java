package practice.mayank.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.mayank.ecommerce.dto.CategoryDto;
import practice.mayank.ecommerce.dto.ProductDto;
import practice.mayank.ecommerce.dto.UserRequest;
import practice.mayank.ecommerce.dto.UserResponse;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.entity.Product;
import practice.mayank.ecommerce.entity.User;


@Mapper(componentModel = "spring")
public interface GenericMapper {

    UserResponse userToUserResponse (User user);

    User userRequestToUser(UserRequest userRequest);

    @Mapping(source = "categoryDto" , target = "category")
    Product productDtoToProduct(ProductDto productDto);

    @Mapping(source = "category" , target = "categoryDto")
    ProductDto productToProductDto(Product product);

    Category categoryDtoToCategory(CategoryDto categoryDto);


    CategoryDto categoryToCategoryDto(Category category);
}

