package com.prototype.product.delete;


import com.prototype.product.ProductHelper;
import com.prototype.product.domain.*;
import com.prototype.product.service.ProductService;
import com.prototype.user.service.UserDto;
import com.prototype.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;


    @DisplayName("Delete Test 1. Normal Condition")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        Long productId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName),
                ProductPrice.create(productPrice), SellerId.create(userId),
                Stock.create(stock, isStockInfinite)
        );


        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);
        when(userService.getLoggedInUser()).thenReturn(userDto);

        sut.delete(productId);

        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("Delete Test 2. Abnormal Condition - already deleted")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        Long productId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName),
                ProductPrice.create(productPrice), SellerId.create(userId),
                Stock.create(stock, isStockInfinite)
        );
        productHelper.delete();


        assertThrows(IllegalArgumentException.class, ()->sut.delete(productId));
    }

    @DisplayName("Delete Test 3. Abnormal Condition - product doesn't exist")
    @Test
    public void test3() {

        ProductService sut = new ProductService(productRepository, userService);

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, ()->sut.delete(productId));
    }

}
