package practice.mayank.ecommerce.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.mayank.ecommerce.dto.product.ProductRequest;
import practice.mayank.ecommerce.dto.product.ProductResponse;
import practice.mayank.ecommerce.entity.Product;
import practice.mayank.ecommerce.repository.ProductRepository;

import static org.mockito.Mockito.*;
@SpringBootTest
class ProductTest {


    @Autowired
    private ProductService productService;




}
