package com.prototype.product.service;


import lombok.Value;

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

}
