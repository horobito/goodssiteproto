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

    public ProductDto delete(Long productId) {
        ProductStrategy productDeleteStrategy = Product::delete;
        return doStrategy(productId, productDeleteStrategy);
    }

    public ProductDto revive(Long productId){
        ProductStrategy productReviveStrategy = Product::revive;
        return doStrategy(productId, productReviveStrategy);
    }

    public ProductDto changeStock(Long productId, int amountOfChange){
        ProductStrategy productChangeStockStrategy = product -> product.changeStock(product.getStock()+amountOfChange);
        return doStrategy(productId, productChangeStockStrategy);
    }

    public ProductDto changeProductName(Long productId, String newProductName){
        ProductStrategy productRenameStrategy = product -> product.changeProductName(newProductName);
        return doStrategy(productId, productRenameStrategy);
    }

    public ProductDto changeProductPrice(Long productId, int productPrice){
        ProductStrategy productPriceChangeStrategy = product -> product.changeProductPrice(productPrice);
        return doStrategy(productId, productPriceChangeStrategy);
    }

    public ProductDto setSoldOutState(Long productId, boolean isSoldOut){
        ProductStrategy productSetSoldOutStateStrategy = product -> {
            if (isSoldOut){
                product.setSoldOut();
            }else {
                product.setUnSoldOut();
            }
        };
        return doStrategy(productId, productSetSoldOutStateStrategy);
    }

    public ProductDto update(Long productId, String productName, int productPrice, int amountOfChange, boolean isStockInfinite) {
        ProductStrategy updateStrategy = product -> {
            product.changeProductName(productName);
            product.changeProductPrice(productPrice);
            product.changeStock(amountOfChange);
            product.changeStockInfiniteState(isStockInfinite);
        };
        return doStrategy(productId, updateStrategy);
    }


    public ProductDto setStockInfiniteState(Long productId, boolean isStockInfinite){
        ProductStrategy productSetStockInfiniteStateStrategy = product -> {
            if (isStockInfinite){
                product.setStockInfinite();
            }else {
                product.setStockFinite();
            }
        };
        return doStrategy(productId, productSetStockInfiniteStateStrategy);
    }

    public ProductDto doStrategy(Long productId, ProductStrategy productStrategy){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            productStrategy.executeStrategy(product.get());
            return getProductDto(productRepository.save(product.get()));
        }
        throw new IllegalArgumentException();
    }


    private Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }


    private void checkUserValidation(Long sellerId) {
        Long userId= 1L;
        if (!sellerId.equals(userId)){
            throw new IllegalArgumentException();
        }
    }


    private ProductDto getProductDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getSellerId(),
                "temp",
                product.getStock(),
                product.isStockInfinite(),
                product.isSoldOut(),
                product.isDeleted()
        );
    }


}
