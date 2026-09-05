package practice.mayank.ecommerce.controller.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.category.CategoryRequest;
import practice.mayank.ecommerce.dto.category.CategoryResponse;
import practice.mayank.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get-all")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(

            @RequestParam(name = "pn")
            @PositiveOrZero(message = "Page number should me more than equal to 0")
            int pageNumber,

            @RequestParam(name = "ps")
            @Positive(message  = "Page Size should be greater than 0")
            @Max(value = 20, message = "Page Size should be less than or equal to 20")
            int pageSize

    ) {
        Page<CategoryResponse> allCategories = categoryService.getAllCategories(pageNumber, pageSize);
        return ResponseEntity.ok(allCategories);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse newCategory = categoryService.createNewCategory(categoryRequest);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryName}")
    public ResponseEntity<CategoryResponse> updateCategory(

            @PathVariable
            String categoryName,

            @Valid
            @RequestBody
            CategoryRequest categoryDto

    ) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryName, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/delete/{categoryName}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable
            String categoryName
    ) {
        categoryService.deleteCategories(categoryName);
        return ResponseEntity.noContent().build();
    }


}
