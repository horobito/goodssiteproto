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

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        categories.add("1234567890");
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

    @DisplayName("Create test 2 . normal condition -Duplicate exclusion verification ")
    @Test
    public void test2(){
        CategoryService sut = new CategoryService(categoryRepository);

        List<String> categories = new ArrayList<>();
        categories.add("fantasy");
        categories.add("new");
        categories.add("horror");

        List<Category> existingCategories = new ArrayList<>();
        existingCategories.add(new CategoryHelper(CategoryName.create("fantasy")));
        existingCategories.add(new CategoryHelper(CategoryName.create("horror")));

        List<Category> newCategories = new ArrayList<>();
        existingCategories.add(new CategoryHelper(CategoryName.create("new")));

        when(categoryRepository.findByCategoryNameIn(any())).thenReturn(existingCategories);
        when(categoryRepository.saveAll(any())).thenReturn(newCategories);

        sut.createCategories(categories);

        verify(categoryRepository, times(1)).saveAll(any());
    }

    @DisplayName("Create test 3 . Abnormal condition - name length is too short")
    @Test
    public void test3(){
        CategoryService sut = new CategoryService(categoryRepository);

        List<String> categories = new ArrayList<>();
        categories.add("");
        assertThrows(IllegalArgumentException.class, ()->sut.createCategories(categories));
    }

    @DisplayName("Create test 4 . Abnormal condition - name length is too long")
    @Test
    public void test4(){
        CategoryService sut = new CategoryService(categoryRepository);

        List<String> categories = new ArrayList<>();
        categories.add("1234567890*");
        assertThrows(IllegalArgumentException.class, ()->sut.createCategories(categories));
    }


}
