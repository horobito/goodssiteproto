package com.prototype.product.setStockInfiniteState;


import com.prototype.product.ProductHelper;
import com.prototype.product.domain.*;
import com.prototype.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetStockInfiniteStateTest {

    @Mock
    ProductRepository productRepository;

    @DisplayName("SetStockInfiniteState Test 1. Normal Condition - set stock Infinite ")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        Long productId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.setStockInfiniteState(productId, true);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("SetStockInfiniteState Test 2. Normal Condition - set stock finite ")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        Long productId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );

        productHelper.setStockInfinite();
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.setStockInfiniteState(productId, false);
        verify(productRepository, times(1)).save(any());
    }


    @DisplayName("SetStockInfiniteState Test 3. Abnormal Condition - already stock is finite ")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        Long productId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );

        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));

       assertThrows(IllegalArgumentException.class,
               ()->sut.setStockInfiniteState(productId, false));
    }


    @DisplayName("SetStockInfiniteState Test 4. Abnormal Condition - already stock is infinite ")
    @Test
    public void test4() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        Long productId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );

        productHelper.setStockInfinite();
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));

        assertThrows(IllegalArgumentException.class,
                ()->sut.setStockInfiniteState(productId, true));
    }
}
