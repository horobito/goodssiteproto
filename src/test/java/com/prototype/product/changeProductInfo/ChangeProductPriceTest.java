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
public class ChangeProductPriceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;

    @DisplayName("ChangeProductPrice Test 1. Normal Condition - price is zero ")
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long sellerId = 1L;

        Long productId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName), ProductPrice.create(productPrice),
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

        String newName = "newName";
        int newPrice = 0;
        int amountOfStockChange = 0;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.update(1L, newName, newPrice, amountOfStockChange, newIsStockInfinite, newImageUrl);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("ChangeProductPrice Test 2. Normal Condition - price is over zero")
    @Test
    public void test2() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long sellerId = 1L;

        Long productId = 1L;
        String imageUrl = "temp";

        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName), ProductPrice.create(productPrice),
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

        String newName = "newName";
        int newPrice = 2;
        int amountOfStockChange = 1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.update(1L, newName, newPrice, amountOfStockChange, newIsStockInfinite, newImageUrl);
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("ChangeProductPrice Test 3. Abnormal Condition - price is under zero ")
    @Test
    public void test3() {
        ProductService sut = new ProductService(productRepository, userService);

        String productName = "testName 1";
        int productPrice = 1;
        int stock = 1;
        boolean isStockInfinite = false;
        Long sellerId = 1L;

        Long productId = 1L;
        String imageUrl = "temp";

        UserDto userDto = new UserDto(
                sellerId,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        String newName = "newName";
        int newPrice = -1;
        int amountOfStockChange = stock+1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";


        ProductHelper productHelper = ProductHelper.create(
                productId, ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(sellerId), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        assertThrows(IllegalArgumentException.class,
                ()->        sut.update(1L, newName, newPrice, amountOfStockChange, newIsStockInfinite, newImageUrl));
    }
}
