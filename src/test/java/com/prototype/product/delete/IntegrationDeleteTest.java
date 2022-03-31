package com.prototype.product.delete;


import com.prototype.product.domain.Product;
import com.prototype.product.domain.ProductRepository;
import com.prototype.product.service.ProductDto;
import com.prototype.product.service.ProductService;
import com.prototype.user.service.UserDto;
import com.prototype.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IntegrationDeleteTest {

    @Autowired
    ProductRepository productRepository;

    @Mock
    UserService userService;


    @DisplayName("Integration Delete Test")
    @Transactional
    @Test
    public void test1() {
        ProductService sut = new ProductService(productRepository, userService);

        UserDto loggedInUser = new UserDto(
                1L,
                "username 1",
                "password 1",
                "MALE",
                false,
                LocalDate.now()
        );

        Long expectedProductId =1L;

        Product expected = productRepository.findById(expectedProductId).get();

        ProductDto expectedDto = new ProductDto(
                expected.getProductId(),
                expected.getProductName(),
                expected.getProductPrice(),
                loggedInUser.getUserId(),
                loggedInUser.getUsername(),
                expected.getStockAmount(),
                expected.isStockInfinite(),
                expected.isSoldOut(),
                true,
                expected.getImageUrl().showValue()
        );



        when(userService.getLoggedInUser()).thenReturn(loggedInUser);

        ProductDto deleted = sut.delete(expectedProductId);

        assertEquals(expectedDto.getProductId(), deleted.getProductId());
        assertEquals(expectedDto.getProductName(), deleted.getProductName());
        assertEquals(expectedDto.getProductPrice(), deleted.getProductPrice());
        assertEquals(expectedDto.getSellerId(), deleted.getSellerId());
        assertEquals(expectedDto.getStock(), deleted.getStock());
        assertEquals(expectedDto.isStockInfinite(), deleted.isStockInfinite());
        assertEquals(expectedDto.isSoldOut(), deleted.isSoldOut());
        assertEquals(expectedDto.isDeleted(), deleted.isDeleted());

    }
}
