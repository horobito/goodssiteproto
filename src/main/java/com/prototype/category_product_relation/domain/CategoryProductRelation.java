package com.prototype.category_product_relation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CategoryProductRelationId.class)
@NoArgsConstructor
@Getter
public class CategoryProductRelation {

    @Id
    private Long categoryId;


    @Id
    private Long productId;

    private boolean isDeleted;

    private CategoryProductRelation(Long categoryId, Long productId) {
        this.categoryId = categoryId;
        this.productId = productId;
        this.isDeleted = false;
    }

    public static CategoryProductRelation create(Long categoryId, Long productId){
        return new CategoryProductRelation(categoryId, productId);
    }

    public void delete() {
        if (this.isDeleted) {
            throw new IllegalArgumentException();
        }
        isDeleted = true;
    }

    public void revive() {
        if (!this.isDeleted) {
            throw new IllegalArgumentException();
        }
        isDeleted = false;
    }
}
