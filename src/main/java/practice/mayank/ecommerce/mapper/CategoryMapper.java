package practice.mayank.ecommerce.mapper;

import org.mapstruct.Mapper;
import practice.mayank.ecommerce.dto.category.CategoryRequest;
import practice.mayank.ecommerce.dto.category.CategoryResponse;
import practice.mayank.ecommerce.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category categoryRequestToCategory(CategoryRequest categoryDto);


    CategoryResponse categoryToCategoryResponse(Category category);

}
