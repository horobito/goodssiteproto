package com.prototype.product.create;


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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;


    @DisplayName("Create Test 1. Normal Condition")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 2L;
        String imageUrl = "tempUrl";

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
                SellerId.create(userId), Stock.create(stock, isStockInfinite),  ImageUrl.create(imageUrl)
        );

        when(productRepository.save(any())).thenReturn(productHelper);
        when(userService.getLoggedInUser()).thenReturn(userDto);

        sut.create(productName, productPrice, stock, isStockInfinite, imageUrl);
        verify(productRepository, times(1)).save(any());


    }

    @DisplayName("Create Test 1.5 Normal Condition - length limit test")
    @Test
    public void test11() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 2L;
        String imageUrl = "tempUrl";


        String productNameHundred
                =
                "12345678901234567890123456789012345678901234567890" +
                        "12345678901234567890123456789012345678901234567890";

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        ProductHelper productHelper2 = ProductHelper.create(
                1L, ProductName.create(productNameHundred), ProductPrice.create(productPrice),
                SellerId.create(userId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        when(productRepository.save(any())).thenReturn(productHelper2);
        when(userService.getLoggedInUser()).thenReturn(userDto);

        sut.create(productNameHundred, productPrice, stock, isStockInfinite, imageUrl);
        verify(productRepository, times(1)).save(any());

    }

    @DisplayName("Create Test 2. Abnormal Condition - name length error ")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository, userService);

        String productNameUnder = "";
        String productNameOver
                =
                "12345678901234567890123456789012345678901234567890" +
                        "12345678901234567890123456789012345678901234567890*";


        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        String imageUrl = "tempUrl";

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );


        when(userService.getLoggedInUser()).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class,
                () -> sut.create(productNameUnder, productPrice, stock, isStockInfinite, imageUrl));


    }

    @DisplayName("Create Test 3. Abnormal Condition - price range error ")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = -1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 2L;
        String imageUrl = "tempUrl";

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );


        when(userService.getLoggedInUser()).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class,
                () -> sut.create(productName, productPrice, stock, isStockInfinite, imageUrl));
    }

    @DisplayName("Create Test 4. Abnormal Condition - stock range error ")
    @Test
    public void test4() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = -1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long userId = 2L;
        String imageUrl = "tempUrl";

        UserDto userDto = new UserDto(
                1L,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );


        when(userService.getLoggedInUser()).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class,
                () -> sut.create(productName, productPrice, stock, isStockInfinite, imageUrl));
    }

}
