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
public class ChangeProductNameTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;


    @DisplayName("ChangeProductName Test 1. Normal Condition ")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;
        String imageUrl = "temp";


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
                SellerId.create(userId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);
        when(userService.getLoggedInUser()).thenReturn(userDto);

        String newName = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        int newPrice = 1;
        int amountOfStockChange = 1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";

        sut.update(1L, newName, newPrice, amountOfStockChange, newIsStockInfinite, newImageUrl);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("ChangeProductName Test 2. Abnormal Condition - empty Name ")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        String newName = "";
        int newPrice = 1;
        int amountOfStockChange = 1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(userService.getLoggedInUser()).thenReturn(userDto);
        assertThrows(IllegalArgumentException.class,
                ()->sut.update(1L, newName, newPrice, amountOfStockChange, newIsStockInfinite, newImageUrl));
    }

    @DisplayName("ChangeProductName Test 3. Abnormal Condition - Long Name ")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                1L, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );
        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        String newName = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890*";
        int newPrice = 1;
        int amountOfStockChange = 1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";


        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(userService.getLoggedInUser()).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class,
                ()->sut.update(1L, newName, newPrice, amountOfStockChange, newIsStockInfinite, newImageUrl));
    }


}
