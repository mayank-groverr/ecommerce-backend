package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.category.CategoryRequest;
import practice.mayank.ecommerce.dto.category.CategoryResponse;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.CategoryMapper;
import practice.mayank.ecommerce.repository.CategoryRepository;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.security.services.AuthenticatedUserProvider;
import practice.mayank.ecommerce.security.services.PermissionService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Transactional
    public CategoryResponse createNewCategory(CategoryRequest categoryDto) {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT);
        Category category = categoryMapper.categoryRequestToCategory(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryResponse(newCategory);
    }

    public Page<CategoryResponse> getAllCategories(int pageNumber, int pageSize) {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT);
        Page<Category> allCategories = categoryRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return allCategories.map(categoryMapper::categoryToCategoryResponse);
    }


    @Transactional
    public CategoryResponse updateCategory(String categoryName, CategoryRequest categoryRequest) {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT);
        Category categoryInDb = getCategoryByName(categoryName);
        categoryInDb.setCategoryName(categoryRequest.categoryName());
        return categoryMapper.categoryToCategoryResponse(categoryInDb);
    }


    @Transactional
    public void deleteCategories(String categoryName) {
        PermissionService.hasPermission(authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT);
        Category categoryByName = getCategoryByName(categoryName);
        setProductCategoryToNull(categoryByName);
        categoryRepository.deleteByCategoryName(categoryByName.getCategoryName());
    }

    public Category getCategoryByName(String categoryName) {
        Optional<Category> categoryInDb = categoryRepository.findByCategoryName(categoryName);
        return categoryInDb.orElseThrow(() -> new ResourceNotFoundException("No category Found for this name: " + categoryName));
    }


    private void setProductCategoryToNull(Category category) {
        category.getProducts().forEach(product -> product.setCategory(null));
    }

}

