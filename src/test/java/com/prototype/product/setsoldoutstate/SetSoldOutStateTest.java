package com.prototype.product.setsoldoutstate;

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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SetSoldOutStateTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;


    @DisplayName("SetSoldOutState Test 1. Normal Condition - setSoldOut")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
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

        sut.setSoldOutState(1L, true);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("SetSoldOutState Test 2. Normal Condition - setUnsoldOut")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );
        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        productHelper.setSoldOut();
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);
        when(userService.getLoggedInUser()).thenReturn(userDto);


        sut.setSoldOutState(1L, false);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("SetSoldOutState Test 3. Abnormal Condition - already SoldOut")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        when(userService.getLoggedInUser()).thenReturn(userDto);
        productHelper.setSoldOut();
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        assertThrows(IllegalArgumentException.class, ()->sut.setSoldOutState(1L, true));
    }

    @DisplayName("SetSoldOutState Test 4. Abnormal Condition - already unSoldOut")
    @Test
    public void test4() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite)
        );

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        assertThrows(IllegalArgumentException.class, ()->sut.setSoldOutState(1L, false));
    }


}
