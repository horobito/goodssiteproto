package com.prototype.category.delete;


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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTest {


    @Mock
    CategoryRepository categoryRepository;


    @DisplayName("Delete Test : Normal Condition")
    @Test
    public void test1(){
        CategoryService sut = new CategoryService(categoryRepository);


        CategoryName categoryName = CategoryName.create("category1");

        Long categoryId = 1L;

        Category category = CategoryHelper.create(
                categoryId, categoryName
        );

        when(categoryRepository.findByCategoryIdAndIsDeleted(categoryId, false)).thenReturn(Optional.of(category));
        sut.delete(categoryId);
        verify(categoryRepository, times(1)).save(any());
    }
}
