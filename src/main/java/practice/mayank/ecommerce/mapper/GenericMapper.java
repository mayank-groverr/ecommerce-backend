package practice.mayank.ecommerce.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.mayank.ecommerce.dto.*;
import practice.mayank.ecommerce.dto.category.CategoryRequest;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.dto.user.UserRequest;
import practice.mayank.ecommerce.dto.user.UserResponse;
import practice.mayank.ecommerce.entity.*;


@Mapper(componentModel = "spring")
public interface GenericMapper {

    UserResponse userToUserResponse (User user);

    User userRequestToUser(UserRequest userRequest);

    @Mapping(source = "categoryResponse" , target = "category")
    Product productDtoToProduct(ProductResponse productResponse);

    @Mapping(source = "category" , target = "categoryResponse")
    ProductResponse productToProductDto(Product product);

    Category categoryDtoToCategory(CategoryRequest categoryDto);


    CategoryRequest categoryToCategoryDto(Category category);

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

