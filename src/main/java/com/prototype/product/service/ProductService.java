package com.prototype.product.service;


import com.prototype.product.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    // manager 영역에서 사용할 것들

    public ProductDto create(String productName, int productPrice, int stock, boolean isStockInfinite) {
        Long userid = 1L;
        Product newProduct = Product.create(
                ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userid), Stock.create(stock, isStockInfinite)
        );

        Product saved = productRepository.save(newProduct);

        return getProductDto(saved);
    }

    public ProductDto productContext(Long productId, ProductStrategy productStrategy){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            productStrategy.executeStrategy(product.get());
            product.get().delete();
            return getProductDto(productRepository.save(product.get()));
        }
        throw new IllegalArgumentException();
    }

    public ProductDto delete(Long productId) {
        ProductStrategy productDeleteStrategy = Product::delete;
        return productContext(productId, productDeleteStrategy);
    }

    public ProductDto revive(Long productId){
        ProductStrategy productReviveStrategy = Product::revive;
        return productContext(productId, productReviveStrategy);
    }

    public ProductDto changeStock(Long productId, int amountOfChange){
        ProductStrategy productChangeStockStrategy = product -> product.changeStock(product.getStock()+amountOfChange);
        return productContext(productId, productChangeStockStrategy);
    }

    public ProductDto changeProductName(Long productId, String newProductName){
        ProductStrategy productRenameStrategy = product -> product.changeProductName(newProductName);
        return productContext(productId, productRenameStrategy);
    }

    public ProductDto changeProductPrice(Long productId, int productPrice){
        ProductStrategy productPriceChangeStrategy = product -> product.changeProductPrice(productPrice);
        return productContext(productId, productPriceChangeStrategy);
    }

    public ProductDto setSoldOutState(Long productId, boolean isSoldOut){
        ProductStrategy productSetSoldOutStateStrategy = product -> {
            if (isSoldOut){
                product.setSoldOut();
            }else {
                product.setUnSoldOut();
            }
        };
        return productContext(productId, productSetSoldOutStateStrategy);
    }

    public ProductDto setStockInfiniteState(Long productId, boolean isStockInfinite){
        ProductStrategy productSetStockInfiniteStateStrategy = product -> {
            if (isStockInfinite){
                product.setStockInfinite();
            }else {
                product.setStockFinite();
            }
        };
        return productContext(productId, productSetStockInfiniteStateStrategy);
    }


    private Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }


    private void checkUserValidation(Long sellerId) {
        Long userId= 1L;
        if (sellerId.equals(userId)){
            throw new IllegalArgumentException();
        }
    }


    private ProductDto getProductDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getSellerId(),
                product.getStock(),
                product.isStockInfinite(),
                product.isSoldOut(),
                product.isDeleted()
        );
    }
}
