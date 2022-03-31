package com.prototype.product.service;


import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ProductDto {
    Long productId;

    String productName;

    int productPrice;

    Long sellerId;

    String sellerName;

    int stock;

    boolean isStockInfinite;

    boolean isSoldOut;

    boolean isDeleted;

    String imageUrl;

    LocalDateTime registTime;

}
