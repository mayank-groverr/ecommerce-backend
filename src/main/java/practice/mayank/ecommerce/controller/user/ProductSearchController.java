package practice.mayank.ecommerce.controller.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.dto.product.ProductSearchRequest;
import practice.mayank.ecommerce.service.ProductService;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/public/search-product")
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping()
    public Page<ProductResponse> search(
            @Valid @ModelAttribute ProductSearchRequest request,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return productService.searchProducts(request, page, size, sortBy, direction);
    }
}