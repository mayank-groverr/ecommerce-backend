package practice.mayank.ecommerce.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.CategoryDto;
import practice.mayank.ecommerce.dto.ProductDto;
import practice.mayank.ecommerce.service.CategoryService;


import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto newCategory = categoryService.createNewCategory(categoryDto);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/name/{categoryName}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryName, @RequestBody CategoryDto categoryDto) {
        boolean isUpdated = categoryService.updateCategory(categoryName, categoryDto);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/name/{categoryName}")
    public ResponseEntity<ProductDto> deleteCategory(@PathVariable String categoryName) {
        if (categoryService.deleteCategories(categoryName)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
