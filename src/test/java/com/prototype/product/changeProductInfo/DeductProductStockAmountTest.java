package com.prototype.product.changeProductInfo;

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
public class DeductProductStockAmountTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;

    @DisplayName("Deduction Stock Test 1. Normal Condition :")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 2;
        boolean isStockInfinite = false;
        Long sellerId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(sellerId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        UserDto userDto = new UserDto(
                sellerId,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        int deductedAmount = 1;

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.deductStockAmount(1L, deductedAmount);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("Deduction Stock Test 2. Normal Condition : After deduction, stock amount is zero")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 2;
        boolean isStockInfinite = false;
        Long sellerId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(sellerId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        UserDto userDto = new UserDto(
                sellerId,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        int deductedAmount = stock;

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.deductStockAmount(1L, deductedAmount);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("Deduction Stock Stock 3. Abnormal Condition - deduction amount is zero")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long sellerId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(sellerId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        int deductedAmount = 0;

        UserDto userDto = new UserDto(
                sellerId,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));

        assertThrows(IllegalArgumentException.class,
                ()->sut.deductStockAmount(1L, deductedAmount));


    }

    @DisplayName("Deduction Stock Stock 4. Abnormal Condition - deduction amount is minus")
    @Test
    public void test4() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long sellerId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(sellerId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        int deductedAmount = -1;

        UserDto userDto = new UserDto(
                sellerId,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));

        assertThrows(IllegalArgumentException.class,
                ()->sut.deductStockAmount(1L, deductedAmount));
    }

    @DisplayName("Deduction Stock Stock 5. Abnormal Condition - After deduction, stock amount is minus ")
    @Test
    public void test5() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long sellerId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(sellerId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        int deductedAmount = stock+2;

        UserDto userDto = new UserDto(
                sellerId,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));

        assertThrows(IllegalArgumentException.class,
                ()->sut.deductStockAmount(1L, deductedAmount));


    }
}
