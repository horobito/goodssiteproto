package com.prototype.category.integrationTest;


import com.prototype.category.domain.CategoryRepository;
import com.prototype.category.service.CategoryDto;
import com.prototype.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class getTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void test1(){
        CategoryService sut = new CategoryService(categoryRepository);
        List<CategoryDto> result = sut.getRelatedCategory(1L);
        System.out.println(result.toString());
    }
}
