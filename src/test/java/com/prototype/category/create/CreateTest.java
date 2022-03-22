package com.prototype.category.create;


import com.prototype.category.CategoryHelper;
import com.prototype.category.domain.Category;
import com.prototype.category.domain.CategoryName;
import com.prototype.category.domain.CategoryRepository;
import com.prototype.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class CreateTest {


    @Mock
    CategoryRepository categoryRepository;

    @DisplayName("Create test 1 . normal condition")
    @Test
    public void test1(){
        CategoryService sut = new CategoryService(categoryRepository);

        List<String> categories = new ArrayList<>();
        categories.add("fantasy");
        categories.add("romance");
        categories.add("horror");

        List<Category> newCategories = new ArrayList<>();
        newCategories.add(new CategoryHelper(CategoryName.create("fantasy")));
        newCategories.add(new CategoryHelper(CategoryName.create("romance")));
        newCategories.add(new CategoryHelper(CategoryName.create("horror")));

        when(categoryRepository.findByCategoryNameIn(any())).thenReturn(Collections.emptyList());
        when(categoryRepository.saveAll(any())).thenReturn(newCategories);

        sut.createCategories(categories);

        verify(categoryRepository, times(1)).saveAll(any());


    }


}
