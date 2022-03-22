package com.prototype.product.changeProductName;

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
public class ChangeProductNameTest {

    @Mock
    ProductRepository productRepository;


    @DisplayName("ChangeProductName Test 1. Normal Condition ")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        String newName = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";


        sut.changeProductName(1L, newName);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("ChangeProductName Test 2. Abnormal Condition - empty Name ")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        assertThrows(IllegalArgumentException.class,
                ()->sut.changeProductName(1L, ""));
    }

    @DisplayName("ChangeProductName Test 3. Abnormal Condition - Long Name ")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );

        String newName = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890*";

        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        assertThrows(IllegalArgumentException.class,
                ()->sut.changeProductName(1L, newName));
    }
}
