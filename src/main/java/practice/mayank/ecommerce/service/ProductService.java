package practice.mayank.ecommerce.service;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mayank.ecommerce.dto.product.ProductRequest;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.entity.Product;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.ProductMapper;
import practice.mayank.ecommerce.repository.ProductRepository;
import practice.mayank.ecommerce.util.PatchUtil;
import practice.mayank.ecommerce.validation.handler.CustomValidationHandler;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final CustomValidationHandler customValidationHandler;

    @Transactional
    public ProductResponse addNewProduct(ProductRequest productRequest) {
        Product product = productMapper.productRequestToProductWithoutCategory(productRequest);
        setProductCategory(product, productRequest);
        Product newProduct = productRepository.save(product);
        return productMapper.productToProductResponse(newProduct);
    }

    public Page<ProductResponse> getAllProduct(int pageNumber, int pageSize) {
        Page<Product> allProducts = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return allProducts.map(productMapper::productToProductResponse);
    }


    public ProductResponse getProductById(String productId) {
        Product productInDb = findProductById(productId);
        return productMapper.productToProductResponse(productInDb);
    }

    public Page<ProductResponse> getAllProductsByCategory(String categoryName, int pageNumber, int pageSize) {
        Category category = categoryService.getCategoryByName(categoryName);
        Page<Product> allProducts = productRepository.findAllByCategory(category, PageRequest.of(pageNumber, pageSize));
        return allProducts.map(productMapper::productToProductResponse);
    }

    public Page<ProductResponse> getAllUncategorizedProduct(int pageNumber, int pageSize) {
        Page<Product> productsWithNullCategory = productRepository.findByCategoryIsNull(PageRequest.of(pageNumber, pageSize));
        return productsWithNullCategory.map(productMapper::productToProductResponse);
    }

    @Transactional
    public ProductResponse updateProduct(String productId, JsonPatch jsonPatch)  {
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
        Product productById = findProductById(productId);
        productRepository.delete(productById);
    }

    private Product findProductById(String productId) {
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
