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

    @Embedded
    private ImageUrl imageUrl;

    public ProductHelper(ProductName productName,
                         ProductPrice productPrice, SellerId sellerId,
                         Stock stock, ImageUrl imageUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellerId = sellerId;
        this.stock = stock;
        this.isSoldOut = false;
        this.isDeleted = false;
        this.imageUrl = imageUrl;

    }

    public static ProductHelper create(
            Long id,
            ProductName productName,
            ProductPrice productPrice, SellerId sellerId,
            Stock stock, ImageUrl imageUrl
    ) {
        ProductHelper product = new ProductHelper(
                productName,
                productPrice,
                sellerId,
                stock,
                imageUrl
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

    public void changeImageUrl(String newImageUrl){
        this.imageUrl = ImageUrl.create(newImageUrl);
    }

    public void changeStockInfiniteState(boolean isStockInfinite){
        if (isStockInfinite) {
            this.stock.setStockInfinite();
        }else {
            this.stock.setStockFinite();
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

    public void deductStockAmount(int deductedAmount){
        if (deductedAmount<=0){
            throw new IllegalArgumentException();
        }
        this.stock = Stock.create(this.stock.getRemainingStocks()-deductedAmount, this.isStockInfinite());
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
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

    public int getStockAmount() {
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
