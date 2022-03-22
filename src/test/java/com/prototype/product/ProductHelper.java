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

    public void changeProductName(String productName) {
        this.productName = ProductName.create(productName);
    }

    public void changeProductPrice(int productPrice) {
        this.productPrice = ProductPrice.create(productPrice);
    }

    public void changeStock(int changedStock) {
        if (!this.stock.isStockInfinite()) {
            this.stock = Stock.create(changedStock, this.stock.isStockInfinite());
        }
    }

    public void setSoldOut() {
        if (this.isSoldOut) {
            throw new IllegalArgumentException();
        }
        this.isSoldOut = true;
    }

    public void setUnSoldOut() {
        if (!this.isSoldOut) {
            throw new IllegalArgumentException();
        }
        this.isSoldOut = false;
    }

    private void setId(Long id){
        this.productId = id;
    }

    public void delete() {
        if (this.isDeleted) {
            throw new IllegalArgumentException();
        }
        this.isDeleted = true;
    }

    public void revive() {
        if (!this.isDeleted) {
            throw new IllegalArgumentException();
        }
        this.isDeleted = false;
    }

    public void setStockInfinite() {
        Stock newStock = Stock.create(this.stock.getRemainingStocks(), this.stock.isStockInfinite());
        newStock.setStockInfinite();
        this.stock = newStock;
    }

    public void setStockFinite() {
        Stock newStock = Stock.create(this.stock.getRemainingStocks(), this.stock.isStockInfinite());
        newStock.setStockFinite();
        this.stock = newStock;
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
