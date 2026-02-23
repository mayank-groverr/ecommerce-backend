package practice.mayank.ecommerce.controller.admin;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.product.ProductRequest;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.service.ProductService;


@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String productId) {
        ProductResponse productById = productService.getProductById(productId);
        return ResponseEntity.ok(productById);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @PositiveOrZero
            @RequestParam(name = "pn")
            int pageNumber,

            @Positive
            @RequestParam(name = "ps")
            @Max(20)
            int pageSize
    ) {

        Page<ProductResponse> allProducts = productService.getAllProduct(pageNumber, pageSize);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/get-all-by-category")
    public ResponseEntity<Page<ProductResponse>> getAllProductsByCategory(
            @RequestParam(name = "category")
            @Size(min = 1, max = 100, message = "Category name must be between 1 and 100 characters")
            String categoryName,

            @PositiveOrZero
            @RequestParam(name = "pn")
            int pageNumber,

            @Positive
            @RequestParam(name = "ps")
            @Max(20)
            int pageSize

    ) {
        Page<ProductResponse> allProductsByCategory = productService.getAllProductsByCategory(categoryName, pageNumber, pageSize);
        return ResponseEntity.ok(allProductsByCategory);
    }

    @GetMapping("/get-all-uncategorized")
    public ResponseEntity<Page<ProductResponse>> getAllUncategorizedProducts(
            @PositiveOrZero
            @RequestParam(name = "pn")
            int pageNumber,

            @Positive
            @RequestParam(name = "ps")
            @Max(20)
            int pageSize
    ) {
        Page<ProductResponse> uncategorizedProduct = productService.getAllUncategorizedProduct(pageNumber, pageSize);
        return ResponseEntity.ok(uncategorizedProduct);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
            @Validated
            @RequestBody
            ProductRequest productRequest
    ) {
        ProductResponse newProduct = productService.addNewProduct(productRequest);
        return ResponseEntity.ok(newProduct);
    }

    @PatchMapping(value = "/update/{productId}", consumes = "application/json-patch+json")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable
            String productId,

            @RequestBody
            JsonPatch jsonPatch
    ) {
        ProductResponse updatedProduct = productService.updateProduct(productId, jsonPatch);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
