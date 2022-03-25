package com.prototype.product.revive;

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
public class ReviveTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;


    @DisplayName("Revive Test 1. Normal Condition")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );
        productHelper.delete();
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);
        when(userService.getLoggedInUser()).thenReturn(userDto);

        sut.revive(1L);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("Revive Test 2. Abnormal Condition - non deleted")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(userService.getLoggedInUser()).thenReturn(userDto);


        assertThrows(IllegalArgumentException.class, ()->sut.revive(1L));

    }

}
