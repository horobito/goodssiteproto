package com.prototype.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    private Product(ProductName productName,
                    ProductPrice productPrice, SellerId sellerId,
                    Stock stock) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellerId = sellerId;
        this.stock = stock;
        this.isSoldOut = false;
        this.isDeleted = false;
    }

    public static Product create(ProductName productName,
                                 ProductPrice productPrice, SellerId sellerId,
                                 Stock stock) {
        return new Product(productName, productPrice, sellerId, stock);
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
        Stock newStock = Stock.create(this.stock.getRemainingStocks());
        newStock.setStockInfinite();
        this.stock = newStock;
    }

    public void setStockFinite() {
        Stock newStock = Stock.create(this.stock.getRemainingStocks());
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
