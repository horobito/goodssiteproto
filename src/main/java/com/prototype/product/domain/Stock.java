package com.prototype.product.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.Embeddable;

@Embeddable
@Getter(AccessLevel.PACKAGE)

@NoArgsConstructor
public class Stock {

    private int stock;

    private boolean isStockInfinite;

     private Stock(int stock) {
        this.stock = stock;
        this.isStockInfinite=false;
    }

    public static Stock create(int stock){
         checkStockValidation(stock);
         return new Stock(stock);
    }

    private static void checkStockValidation(int stock){
         if (stock<0){
             throw new IllegalArgumentException();
         }
    }

    public void setStockInfinite(){
        if (this.isStockInfinite){
            throw new IllegalArgumentException();
        }
        this.isStockInfinite = true;
    }

    public void setStockFinite(){
         if (!this.isStockInfinite){
             throw new IllegalArgumentException();
         }
         this.isStockInfinite= false;
    }
}
