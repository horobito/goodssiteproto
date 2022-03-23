package com.prototype.category_product_relation.service;


import com.prototype.category_product_relation.domain.CategoryProductRelation;
import com.prototype.category_product_relation.domain.CategoryProductRelationId;
import com.prototype.category_product_relation.domain.CategoryProductRelationRepository;
import com.prototype.category_product_relation.domain.CategoryProductRelationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryProductRelationService {

    private final CategoryProductRelationRepository relationRepository;


    public List<RelationDto> createRelations(List<Long> categoryIds, Long productId){
        List<CategoryProductRelation> existingRelations = relationRepository.findByProductIdAndCategoryIdIn(productId, categoryIds);
        List<CategoryProductRelation> newRelations = excludeDuplication(categoryIds, productId, existingRelations);
        return getRelationDtos(joinRelations( relationRepository.saveAll(newRelations), existingRelations));
    }


    public void delete(Long categoryId, Long productId){
        CategoryProductRelationStrategy deleteStrategy = CategoryProductRelation::delete;
        doStrategy(deleteStrategy, categoryId, productId);
    }

    public void revive(Long categoryId, Long productId){
        CategoryProductRelationStrategy reviveStrategy = CategoryProductRelation::revive;
        doStrategy(reviveStrategy, categoryId, productId);
    }

    public void doStrategy(CategoryProductRelationStrategy strategy, Long categoryId, Long productId){
        Optional<CategoryProductRelation> relation = relationRepository.findById(new CategoryProductRelationId(categoryId, productId));
        if (relation.isPresent()){
            strategy.doStrategy(relation.get());
            relationRepository.save(relation.get());
        }else {
            throw new IllegalArgumentException();
        }
    }






    private List<CategoryProductRelation> joinRelations(List<CategoryProductRelation> newRelations, List<CategoryProductRelation> existingRelations) {
        return Stream.concat(existingRelations.stream(), newRelations.stream()).collect(Collectors.toList());
    }

    private List<CategoryProductRelation> excludeDuplication(List<Long> categoryIds, Long productId, List<CategoryProductRelation> existingRelations) {
        Set<Long> existingCategoryIds = existingRelations.stream().map(it->it.getCategoryId()).collect(Collectors.toSet());

        return categoryIds.stream().filter(categoryId->
            checkIsNew(categoryId, existingCategoryIds)
        ).map(categoryId->CategoryProductRelation.create(categoryId, productId)).collect(Collectors.toList());
    }

    private boolean checkIsNew(Long categoryId, Set<Long> existingRelations) {
        return !existingRelations.contains(categoryId);
    }


    private List<RelationDto> getRelationDtos(List<CategoryProductRelation> relations) {
        return relations.stream().map(
                it->new RelationDto(it.getCategoryId(), it.getProductId(), it.isDeleted())
                ).collect(Collectors.toList());
    }

}
