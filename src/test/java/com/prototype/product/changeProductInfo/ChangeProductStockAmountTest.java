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
public class ChangeProductStockAmountTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;

    @DisplayName("Change Stock  Test 1. Normal Condition")
    @Test
    public void test1() {
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

        UserDto userDto = new UserDto(
                sellerId,
                "user1",
                "password1",
                "MALE",
                false,
                LocalDate.now()
        );

        int changedStockAmount = 2;
        String newName = "newName";
        int newPrice = 1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";

        when(userService.getLoggedInUser()).thenReturn(userDto);
        when(productRepository.findById(any())).thenReturn(Optional.of(productHelper));
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.update(1L, newName, newPrice, changedStockAmount, newIsStockInfinite, newImageUrl);
        verify(productRepository, times(1)).save(any());


    }

    @DisplayName("Change Stock 2. Normal Condition - new stock amount is zero")
    @Test
    public void test2() {
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

        int changedStockAmount = 0;
        String newName = "newName";
        int newPrice = 1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";

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
        when(productRepository.save(any())).thenReturn(productHelper);

        sut.update(1L, newName, newPrice, changedStockAmount, newIsStockInfinite, newImageUrl);
        verify(productRepository, times(1)).save(any());


    }

    @DisplayName("Change Stock 3. Abnormal Condition - sum is minus")
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

        int changedStockAmount = -1;
        String newName = "newName";
        int newPrice = 1;
        boolean newIsStockInfinite = false;
        String newImageUrl = "temp";


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
                ()->sut.update(1L, newName, newPrice, changedStockAmount, newIsStockInfinite, newImageUrl ));


    }
}
