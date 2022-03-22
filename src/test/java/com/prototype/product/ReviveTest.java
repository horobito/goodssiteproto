package com.prototype.product;

import com.prototype.product.domain.*;
import com.prototype.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ReviveTest {
    @Mock
    ProductRepository productRepository;


    @DisplayName("Revive Test 1. Normal Condition")
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
        productHelper.delete();
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.revive(1L);
        verify(productRepository, times(1)).save(any());
    }

}
