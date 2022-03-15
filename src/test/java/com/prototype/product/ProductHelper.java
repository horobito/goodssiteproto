package com.prototype.product;

import com.prototype.product.domain.*;
import org.thymeleaf.standard.inline.StandardHTMLInliner;

import javax.persistence.Embedded;


public class ProductHelper extends Product {

    private Long productId;

    @Embedded
    private ProductName productName;

    @Embedded
    private ProductPrice productPrice;

    @Embedded
    private SellerId sellerId;

    @Embedded
    private Stock stock;

    private boolean isSoldOut;

    private boolean isDeleted;

    public ProductHelper(ProductName productName,
                         ProductPrice productPrice, SellerId sellerId,
                         Stock stock) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellerId = sellerId;
        this.stock = stock;
        this.isSoldOut = false;
        this.isDeleted = false;

    }

    public static ProductHelper create(
            Long id,
            ProductName productName,
            ProductPrice productPrice, SellerId sellerId,
            Stock stock
    ) {
        ProductHelper product = new ProductHelper(
                productName,
                productPrice,
                sellerId,
                stock
        );

        product.setId(id);
        return product;

    }

    private void setId(Long id){
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return this.productName.getProductName();
    }

    public int getProductPrice() {
        return this.productPrice.getProductPrice();
    }

    public Long getSellerId() {
        return this.sellerId.getSellerId();
    }

    public int getStock() {
        return this.stock.getRemainingStocks();
    }

    public boolean isStockInfinite() {
        return this.stock.isStockInfinite();
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
