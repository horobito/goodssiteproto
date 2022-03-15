package com.prototype.product;


import com.prototype.product.domain.*;
import com.prototype.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    ProductRepository productRepository;


    @DisplayName("Create Test 1. Normal Condition")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 2L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );

        when(productRepository.save(any())).thenReturn(productHelper);

        sut.create(productName, productPrice, stock, isStockInfinite);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("Create Test 2. Abnormal Condition - name length error ")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository);

        String productNameUnder = "";
        String productNameOver
                =
                "12345678901234567890123456789012345678901234567890" +
                        "12345678901234567890123456789012345678901234567890*";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;

        assertThrows(IllegalArgumentException.class,
                ()->sut.create(productNameUnder, productPrice, stock, isStockInfinite));


        assertThrows(IllegalArgumentException.class,
                ()->sut.create(productNameOver, productPrice, stock, isStockInfinite));

    }

    @DisplayName("Create Test 3. Abnormal Condition - price range error ")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = -1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 2L;

        assertThrows(IllegalArgumentException.class,
                ()->sut.create(productName, productPrice, stock, isStockInfinite));

    }

}
