package com.prototype.category_product_relation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@NoArgsConstructor
public class CategoryProductRelationId implements Serializable {

    private Long categoryId;
    private Long productId;


    public CategoryProductRelationId(Long categoryId, Long productId) {
        this.categoryId = categoryId;
        this.productId = productId;
    }



    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            System.out.println("Object Same");
            return true;
        }

        if (!(obj instanceof CategoryProductRelationId)) {
            return false;
        }


        CategoryProductRelationId that = (CategoryProductRelationId) obj;

        if (this.categoryId.equals(that.categoryId) && this.productId.equals(that.productId)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(categoryId, productId);
    }
}
