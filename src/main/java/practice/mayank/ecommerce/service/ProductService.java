package practice.mayank.ecommerce.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.product.ProductRequest;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.dto.product.ProductSearchRequest;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.entity.Product;
import practice.mayank.ecommerce.entity.ProductSpecification;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.ProductMapper;
import practice.mayank.ecommerce.repository.ProductRepository;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.security.services.AuthenticatedUserProvider;
import practice.mayank.ecommerce.security.services.PermissionService;
import practice.mayank.ecommerce.util.PatchUtil;
import practice.mayank.ecommerce.validation.handler.CustomValidationHandler;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {


    private static final Set<String> SORTABLE_FIELDS = Set.of("productName", "productPrice");
    private static final int MAX_PAGE_SIZE = 50;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final CustomValidationHandler customValidationHandler;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    @Transactional
    public ProductResponse addNewProduct(ProductRequest productRequest) {
        PermissionService
                .hasPermission(
                        authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT
                );
        Product product = productMapper.productRequestToProductWithoutCategory(productRequest);
        setProductCategory(product, productRequest);
        Product newProduct = productRepository.save(product);
        return productMapper.productToProductResponse(newProduct);
    }

    public Page<ProductResponse> getAllProduct(int pageNumber, int pageSize) {
        PermissionService
                .hasPermission(
                        authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT
                );
        Page<Product> allProducts = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return allProducts.map(productMapper::productToProductResponse);
    }


    public ProductResponse getProductById(String productId) {
        PermissionService
                .hasPermission(
                        authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT
                );
        Product productInDb = findProductById(productId);
        return productMapper.productToProductResponse(productInDb);
    }

    public Page<ProductResponse> getAllProductsByCategory(String categoryName, int pageNumber, int pageSize) {
        PermissionService
                .hasPermission(
                        authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT
                );
        Category category = categoryService.getCategoryByName(categoryName);
        Page<Product> allProducts = productRepository.findAllByCategory(category, PageRequest.of(pageNumber, pageSize));
        return allProducts.map(productMapper::productToProductResponse);
    }

    public Page<ProductResponse> getAllUncategorizedProduct(int pageNumber, int pageSize) {
        PermissionService
                .hasPermission(
                        authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT
                );
        Page<Product> productsWithNullCategory = productRepository.findByCategoryIsNull(PageRequest.of(pageNumber, pageSize));
        return productsWithNullCategory.map(productMapper::productToProductResponse);
    }


    @Transactional
    public ProductResponse updateProduct(String productId, JsonPatch jsonPatch)  {
        PermissionService
                .hasPermission(
                        authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT
                );
        Product productById = findProductById(productId);
        ProductRequest patchRequest = productMapper.productToProductRequest(productById);
        ProductRequest requestAfterChanges = PatchUtil.applyJsonPatch(jsonPatch, patchRequest, ProductRequest.class);
        customValidationHandler.validate(requestAfterChanges);
        productMapper.updateExistingProduct(requestAfterChanges, productById);
        setProductCategory(productById, requestAfterChanges);
        return productMapper.productToProductResponse(productById);
    }


    @Transactional
    public void deleteProduct(String productId) {
        PermissionService
                .hasPermission(
                        authenticatedUserProvider.getAuthenticatedUser(), PermissionEnum.PRODUCT_AND_CATEGORY_MANAGEMENT
                );
        Product productById = findProductById(productId);
        productRepository.delete(productById);
    }


    public Page<ProductResponse> searchProducts(ProductSearchRequest request,
                                                int pageNumber,
                                                int pageSize,
                                                String sortBy,
                                                String direction) {

        String sortField = SORTABLE_FIELDS.contains(sortBy) ? sortBy : "productName";
        Sort.Direction dir = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC);
        Sort sort = Sort.by(dir, sortField).and(Sort.by("productId")); // tiebreaker for stable paging

        Pageable pageable = PageRequest.of(pageNumber, Math.min(pageSize, MAX_PAGE_SIZE), sort);

        return productRepository
                .findAll(ProductSpecification.from(request), pageable)
                .map(productMapper::productToProductResponse);
    }


    public Product findProductById(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new  ResourceNotFoundException("No product found for productId: " + productId));
    }

    public void setProductCategory(Product product, ProductRequest productRequest){
        if(productRequest.categoryRequest() != null){
            if(
                    product.getCategory() != null &&
                    productRequest.categoryRequest().categoryName().equals(product.getCategory().getCategoryName()))
            {
                return;
            }
            Category categoryByName = categoryService.getCategoryByName(productRequest.categoryRequest().categoryName());
            product.setCategory(categoryByName);
        }else{
            product.setCategory(null);
        }
    }
}
