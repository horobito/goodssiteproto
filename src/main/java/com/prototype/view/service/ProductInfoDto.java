package com.prototype.view.service;

import com.prototype.category.service.CategoryDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class ProductInfoDto {
    Long productId;
    String productName;
    Long sellerId;
    String sellerName;
    double averageScore;
    int reviewCount;
    int remainingStock;
    boolean isStockInfinite;
    boolean isSoldOut;
    boolean isDeleted;
    List<CategoryDto> categories;
    int productPrice;
    String imageUrl;
    LocalDateTime registTime;
}
