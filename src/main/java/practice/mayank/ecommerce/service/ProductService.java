package practice.mayank.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.mayank.ecommerce.dto.CategoryDto;
import practice.mayank.ecommerce.dto.ProductDto;
import practice.mayank.ecommerce.entity.Category;
import practice.mayank.ecommerce.entity.Product;
import practice.mayank.ecommerce.exception.customexception.ResourceNotFoundException;
import practice.mayank.ecommerce.mapper.GenericMapper;
import practice.mayank.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final GenericMapper genericMapper;
    private final CategoryService categoryService;

    public ProductDto addNewProduct(ProductDto productDto) {
        Product product = mapToProductWithExistingCategory(productDto);
        Product newProduct = productRepository.save(product);
        return genericMapper.productToProductDto(newProduct);
    }

    public List<ProductDto> getAllProduct() {
        List<Product> allProducts = productRepository.findAll();
        if (!allProducts.isEmpty()) {
            return allProducts.stream().map(this::mapToProductDtoWithCategory).toList();
        }
        throw new ResourceNotFoundException("No Products Found");
    }


    public ProductDto getProductById(String productId) {
        Optional<Product> productInDb = productRepository.findById(productId);
        if (productInDb.isPresent()) {
            return mapToProductDtoWithCategory(productInDb.get());
        }
        throw new ResourceNotFoundException("Product Not Found");
    }

    public List<ProductDto> getAllProductsByCategory(CategoryDto categoryDto) {
        Category category = categoryService.getCategoryByName(categoryDto.categoryName());
        List<Product> allProducts = productRepository.findAllByCategory(category);
        if (!allProducts.isEmpty()) {
            return allProducts.stream().map(this::mapToProductDtoWithCategory).toList();
        }
        throw new ResourceNotFoundException("No Products Found for this category");
    }

    public ProductDto updateProduct(String productId, ProductDto productDto) {
        Product updatedproduct = genericMapper.productDtoToProduct(productDto);
        Product productInDb = genericMapper.productDtoToProduct(getProductById(productId));
        if (updatedproduct != null) {
            productInDb.setProductName(
                    (updatedproduct.getProductName() != null && !updatedproduct.getProductName().isEmpty()) ?
                            updatedproduct.getProductName() :
                            productInDb.getProductName());

            productInDb.setProductDescription(
                    (updatedproduct.getProductDescription() != null && !updatedproduct.getProductDescription().isEmpty()) ?
                    updatedproduct.getProductDescription() :
                    productInDb.getProductDescription());

            productInDb.setProductPrice(
                    (updatedproduct.getProductPrice() > 0) ?
                            updatedproduct.getProductPrice() :
                            productInDb.getProductPrice());

            productInDb.setProductStock(
                    (updatedproduct.getProductStock() >= 0) ?
                    updatedproduct.getProductStock() :
                    productInDb.getProductStock());


            Product resolvedProduct = mapToProductWithExistingCategory(productDto);
            productInDb.setCategory(resolvedProduct.getCategory());
            Product saved = productRepository.save(productInDb);
            return genericMapper.productToProductDto(saved);
        }
        throw new ResourceNotFoundException("Product Not Found");
    }


    public boolean deleteProduct(String productId) {
        try {
            Product productById = genericMapper.productDtoToProduct(getProductById(productId));
            productRepository.delete(productById);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Product mapToProductWithExistingCategory(ProductDto productDto){
        Category categoryInDB = categoryService.getCategoryByName(productDto.categoryDto().categoryName());
        Product product = genericMapper.productDtoToProduct(productDto);
        product.setCategory(categoryInDB);
        return product;
    }

    private ProductDto mapToProductDtoWithCategory(Product product){
        CategoryDto categoryDto = genericMapper.categoryToCategoryDto(product.getCategory());

        return new ProductDto(product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductPrice(),
                product.getProductStock(),
                product.getImageUrl(),
                categoryDto);
    }


}
