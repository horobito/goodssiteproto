package com.prototype.product.create;


import com.prototype.product.domain.ProductRepository;
import com.prototype.product.service.ProductDto;
import com.prototype.product.service.ProductService;
import com.prototype.user.service.UserDto;
import com.prototype.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IntegrationCreateTest {

    @Autowired
    ProductRepository productRepository;

    @Mock
    UserService userService;

    @DisplayName("Integration Create Test")
    @Test
    public void test1(){

        UserDto loggedInUser = new UserDto(
                1L,
                "username 1",
                "password 1",
                "MALE",
                false,
                LocalDate.now()
        );

        ProductService sut = new ProductService(productRepository, userService);

        String productName = "product1";
        int productPrice = 100;
        int stock = 10;
        boolean isStockInfinite = false;

        Long expectedProductId = 1L;
        Long expectedSellerId = loggedInUser.getUserId();

        String sellerName = loggedInUser.getUsername();


        ProductDto expected = new ProductDto(
                expectedProductId,
                productName,
                productPrice,
                expectedSellerId,
                sellerName,
                stock,
                false,
                false,
                false

        );


        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

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
