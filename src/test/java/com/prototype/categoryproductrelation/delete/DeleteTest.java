package com.prototype.categoryproductrelation.delete;


import com.prototype.category_product_relation.domain.CategoryProductRelation;
import com.prototype.category_product_relation.domain.CategoryProductRelationRepository;
import com.prototype.category_product_relation.service.CategoryProductRelationService;
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
public class DeleteTest {


    @Mock
    CategoryProductRelationRepository relationRepository;

    @DisplayName("Delete Test 1. Normal Condition")
    @Test
    public void test1(){

        CategoryProductRelationService sut = new CategoryProductRelationService(relationRepository);

        Long categoryId = 1L;
        Long productId = 1L;

        CategoryProductRelation relation = CategoryProductRelation.create(categoryId,productId);

        when(relationRepository.findById(any())).thenReturn(Optional.of(relation));

        sut.delete(categoryId, productId);
        verify(relationRepository, times(1)).save(any());
    }

    @DisplayName("Delete Test 2. Abnormal Condition : already deleted")
    @Test
    public void test2(){

        CategoryProductRelationService sut = new CategoryProductRelationService(relationRepository);

        Long categoryId = 1L;
        Long productId = 1L;

        CategoryProductRelation relation = CategoryProductRelation.create(categoryId,productId);
        relation.delete();

        when(relationRepository.findById(any())).thenReturn(Optional.of(relation));

        assertThrows(IllegalArgumentException.class, ()->sut.delete(categoryId, productId));

    }
}
