package com.prototype.product;


import com.prototype.product.domain.ProductRepository;
import com.prototype.product.service.ProductDto;
import com.prototype.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntegrationCreateTest {

    @Autowired
    ProductRepository productRepository;


    @DisplayName("Integration Create Test")
    @Test
    public void test1(){
        ProductService sut = new ProductService(productRepository);

        String productName = "product1";
        int productPrice = 100;
        int stock = 10;
        boolean isStockInfinite = false;

        Long expectedProductId = 1L;
        Long expectedSellerId = 1L;

        ProductDto expected = new ProductDto(
                expectedProductId,
                productName,
                productPrice,
                expectedSellerId,
                stock,
                false,
                false,
                false

        );

        ProductDto created= sut.create(productName, productPrice, stock, isStockInfinite);

        assertEquals(expected.getProductId(), created.getProductId());
        assertEquals(expected.getProductName(), created.getProductName());
        assertEquals(expected.getProductPrice(), created.getProductPrice());
        assertEquals(expected.getSellerId(), created.getSellerId());
        assertEquals(expected.getStock(), created.getStock());
        assertEquals(expected.isStockInfinite(), created.isStockInfinite());
        assertEquals(expected.isSoldOut(), created.isSoldOut());
        assertEquals(expected.isDeleted(), created.isDeleted());

    }
}
