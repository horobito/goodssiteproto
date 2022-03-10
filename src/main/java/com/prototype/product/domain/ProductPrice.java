package com.prototype.product.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class ProductPrice {

    private int productPrice;


    private ProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public static ProductPrice create(int productPrice) {
        checkPriceValidation(productPrice);
        return new ProductPrice(productPrice);
    }

    public static void checkPriceValidation(int productPrice){
        if (productPrice<0){
            throw new IllegalArgumentException();
        }
    }


}
