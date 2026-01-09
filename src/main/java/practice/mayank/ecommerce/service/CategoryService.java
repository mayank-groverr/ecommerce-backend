package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.dto.CategoryDto;
import practice.mayank.ecommerce.dto.ProductDto;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.entity.Product;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.GenericMapper;
import practice.mayank.ecommerce.repository.CategoryRepository;
import practice.mayank.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final GenericMapper genericMapper;

    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        Category category = genericMapper.categoryDtoToCategory(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return genericMapper.categoryToCategoryDto(newCategory);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        if (!allCategories.isEmpty()) {
            return allCategories.stream().map(genericMapper::categoryToCategoryDto).toList();
        }
        throw new ResourceNotFoundException("No Categories Found");
    }


     public Category getCategoryByName(String categoryName) {
        Optional<Category> categoryInDb = categoryRepository.findByCategoryName(categoryName);
        if (categoryInDb.isPresent()) {
            return categoryInDb.get();
        }
        throw new ResourceNotFoundException("No Category found for this Name: " + categoryName);
    }


    public boolean updateCategory(String categoryName, CategoryDto categoryDto) {
        Category categoryInDb = getCategoryByName(categoryName);
        int affectedRows = categoryRepository.updateCategoryByName(categoryInDb.getCategoryName(), categoryDto.categoryName());
        return affectedRows == 1;
    }


    public boolean deleteProduct(String categoryName) {
        try {
            Category categoryByName = getCategoryByName(categoryName);
            categoryRepository.deleteByCategoryName(categoryByName.getCategoryName());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}

