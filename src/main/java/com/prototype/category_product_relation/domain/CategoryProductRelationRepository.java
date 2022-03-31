package com.prototype.category_product_relation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryProductRelationRepository extends JpaRepository<CategoryProductRelation, CategoryProductRelationId> {

    List<CategoryProductRelation> findByProductIdAndCategoryIdIn(Long productId, List<Long> categoryIds);

    List<CategoryProductRelation> findByProductId(Long productId);
}
