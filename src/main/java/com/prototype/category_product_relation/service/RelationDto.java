package com.prototype.category_product_relation.service;

import lombok.Value;

@Value
public class RelationDto {
    Long categoryId;

    Long productId;

    boolean isDeleted;
}
