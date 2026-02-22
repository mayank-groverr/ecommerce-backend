package practice.mayank.ecommerce.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.mayank.ecommerce.dto.*;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.entity.*;


@Mapper(componentModel = "spring")
public interface GenericMapper {

    UserResponse userToUserResponse (User user);

    User userRequestToUser(UserRequest userRequest);

    @Mapping(source = "categoryDto" , target = "category")
    Product productDtoToProduct(ProductResponse productDto);

    @Mapping(source = "category" , target = "categoryDto")
    ProductResponse productToProductDto(Product product);

    Category categoryDtoToCategory(CategoryDto categoryDto);


    CategoryDto categoryToCategoryDto(Category category);

    Cart cartDtoToCart(CartDto cartDto);

    CartDto cartToCartDto(Cart cart);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "product.productId" , source= "productId")
    @Mapping(target = "quantity", source = "quantity")
    CartItem cartItemDtoToCartItem(CartItemDto cartItemDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "productId" , source= "product.productId")
    @Mapping(target = "quantity", source = "quantity")
    CartItemDto cartItemToCartItemDto(CartItem cartItem);
}

