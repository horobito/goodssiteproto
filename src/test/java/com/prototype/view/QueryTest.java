package com.prototype.view;


import com.prototype.product.service.ProductService;
import com.prototype.view.domain.ViewRepository;
import com.prototype.view.service.ProductInfoDto;
import com.prototype.view.service.ViewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QueryTest {


    @Autowired
    ViewRepository viewRepository;

    @Mock
    ProductService productService;

    @Test
    public void test1(){
        ViewService sut = new ViewService(viewRepository, productService);

        List<ProductInfoDto> result = sut.findOrderByTime("2022-03-29 23:47:43.121", 10);



        System.out.println(result);
    }

    @Test
    public void test2(){
        ViewService sut = new ViewService(viewRepository, productService);

        List<ProductInfoDto> result = sut.findOrderByScore( 10);




        System.out.println(result);
    }
}
