package com.prototype.categoryproductrelation.create;


import com.prototype.category_product_relation.domain.CategoryProductRelation;
import com.prototype.category_product_relation.domain.CategoryProductRelationRepository;
import com.prototype.category_product_relation.service.CategoryProductRelationService;
import com.prototype.category_product_relation.service.RelationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    CategoryProductRelationRepository relationRepository;


    @DisplayName("Create test 1. Normal Condition - All relations are new ")
    @Test
    public void test1() {
        CategoryProductRelationService sut = new CategoryProductRelationService(relationRepository);


        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Long productId = 1L;

        List<CategoryProductRelation> existingRelations
                = Arrays.asList(
                CategoryProductRelation.create(1L, productId),
                CategoryProductRelation.create(2L, productId),
                CategoryProductRelation.create(3L, productId)
                );

        when(relationRepository.findByProductIdAndCategoryIdIn(productId, categoryIds))
                .thenReturn(existingRelations);
        when(relationRepository.saveAll(anyCollection())).thenReturn(Collections.emptyList());

        List<RelationDto> result = sut.createRelations(categoryIds, productId);



        verify(relationRepository, times(1)).saveAll(any());
    }

    @DisplayName("Create test 2. Normal Condition - common case")
    @Test
    public void test2() {
        CategoryProductRelationService sut = new CategoryProductRelationService(relationRepository);


        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Long productId = 1L;

        List<CategoryProductRelation> existingRelations
                = Arrays.asList(
                CategoryProductRelation.create(1L, productId),
                CategoryProductRelation.create(2L, productId)
        );

        when(relationRepository.findByProductIdAndCategoryIdIn(productId, categoryIds))
                .thenReturn(existingRelations);

        List<CategoryProductRelation> newRelation = Arrays.asList(CategoryProductRelation.create(3L, productId));
        when(relationRepository.saveAll(anyCollection())).thenReturn(newRelation);

        List<RelationDto> result = sut.createRelations(categoryIds, productId);

        verify(relationRepository, times(1)).saveAll(any());
    }
}
