package com.prototype.product.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Stock {

    private int remainingStocks;

    private boolean isStockInfinite;

     private Stock(int stock, boolean isStockInfinite) {
        this.remainingStocks = stock;
        this.isStockInfinite=isStockInfinite;
    }

    private Stock(int stock) {
        this.remainingStocks = stock;
        this.isStockInfinite=false;
    }

    public static Stock create(int stock){
        checkStockValidation(stock);
        return new Stock(stock);
    }

    public static Stock create(int stock, boolean isStockInfinite){
         checkStockValidation(stock);
         return new Stock(stock, isStockInfinite);
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
