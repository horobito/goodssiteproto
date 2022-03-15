package com.prototype.product.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class SellerId {

    private Long sellerId;

    private SellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public static SellerId create(Long sellerId){
        return new SellerId(sellerId);
    }
}
