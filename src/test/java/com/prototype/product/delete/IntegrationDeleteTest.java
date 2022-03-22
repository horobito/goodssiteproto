package com.prototype.product.delete;


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
public class IntegrationDeleteTest {

    @Autowired
    ProductRepository productRepository;


    @DisplayName("Integration Delete Test")
    @Test
    public void test1() {
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
                true

        );



        ProductDto deleted = sut.delete(expectedProductId);

        assertEquals(expected.getProductId(), deleted.getProductId());
        assertEquals(expected.getProductName(), deleted.getProductName());
        assertEquals(expected.getProductPrice(), deleted.getProductPrice());
        assertEquals(expected.getSellerId(), deleted.getSellerId());
        assertEquals(expected.getStock(), deleted.getStock());
        assertEquals(expected.isStockInfinite(), deleted.isStockInfinite());
        assertEquals(expected.isSoldOut(), deleted.isSoldOut());
        assertEquals(expected.isDeleted(), deleted.isDeleted());

    }
}
