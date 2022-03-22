package com.prototype.product.setsoldoutstate;

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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SetSoldOutStateTest {
    @Mock
    ProductRepository productRepository;


    @DisplayName("SetSoldOutState Test 1. Normal Condition - setSoldOut")
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

        sut.setSoldOutState(1L, true);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("SetSoldOutState Test 2. Normal Condition - setUnsoldOut")
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

        productHelper.setSoldOut();
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);


        sut.setSoldOutState(1L, false);
        verify(productRepository, times(1)).save(any());
    }


}
