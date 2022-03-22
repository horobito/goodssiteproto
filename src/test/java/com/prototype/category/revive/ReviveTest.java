package com.prototype.category.revive;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ReviveTest {

    @Mock
    CategoryRepository categoryRepository;


    @DisplayName("Revive Test1 : Normal Condition")
    @Test
    public void test1(){
        CategoryService sut = new CategoryService(categoryRepository);


        CategoryName categoryName = CategoryName.create("category1");

        Long categoryId = 1L;

        Category category = CategoryHelper.create(
                categoryId, categoryName
        );
        category.delete();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        sut.revive(categoryId);
        verify(categoryRepository, times(1)).save(any());
    }

    @DisplayName("Revive Test2 : Abnormal Condition - category doesn't deleted")
    @Test
    public void test2(){
        CategoryService sut = new CategoryService(categoryRepository);


        CategoryName categoryName = CategoryName.create("category1");

        Long categoryId = 1L;

        Category category = CategoryHelper.create(
                categoryId, categoryName
        );

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        assertThrows(IllegalArgumentException.class, ()->sut.revive(categoryId));

    }
}
