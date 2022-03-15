package com.prototype.product.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class ProductName {

    private String productName;

    private ProductName(String productName) {
        this.productName = productName;
    }

    public static ProductName create(String productName) {
        checkNameValidation(productName);
        return new ProductName(productName);
    }

    private static void checkNameValidation(String productName) {
        if (productName.length()==0 || productName.length()>100){
            throw new IllegalArgumentException();
        }
    }
}
