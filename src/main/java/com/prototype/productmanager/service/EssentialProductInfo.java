package com.prototype.productmanager.service;

import com.prototype.category.service.CategoryDto;
import lombok.Value;

import java.util.List;

@Value
public class EssentialProductInfo {
    Long productId;

    String productName;

    Long sellerId;

    String sellerName;

    int productPrice;

    boolean isSoldOut;

    List<CategoryDto> categoryDtos;

    String imageUrl;

}
