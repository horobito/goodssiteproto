package com.prototype.product.service;


import com.prototype.product.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;


    // manager 영역에서 사용할 것들

    public ProductDto create(String productName, int productPrice, int stock, boolean isStockInfinite) {
        Long userid = 1L;
        Product newProduct = Product.create(
                ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(userid), Stock.create(stock)
        );

        return getProductDto(productRepository.saveAndFlush(newProduct));
    }

    public ProductDto delete(Long productId) {
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            product.get().delete();
            getProductDto(productRepository.saveAndFlush(product.get()));
        }
        throw new IllegalArgumentException();
    }

    public ProductDto revive(Long productId){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            product.get().revive();
            getProductDto(productRepository.saveAndFlush(product.get()));
        }
        throw new IllegalArgumentException();
    }



    public ProductDto changeStock(Long productId, int amountOfChange){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            product.get().changeStock(product.get().getStock()+amountOfChange);
            getProductDto(productRepository.saveAndFlush(product.get()));
        }
        throw new IllegalArgumentException();
    }


    public ProductDto changeProductName(Long productId, String newProductName){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            product.get().changeProductName(newProductName);
            getProductDto(productRepository.saveAndFlush(product.get()));
        }
        throw new IllegalArgumentException();
    }

    public ProductDto changeProductPrice(Long productId, int productPrice){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            product.get().changeProductPrice(productPrice);
            getProductDto(productRepository.saveAndFlush(product.get()));
        }
        throw new IllegalArgumentException();
    }

    public ProductDto setSoldOutState(Long productId, boolean isSoldOut){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            if (isSoldOut){
                product.get().setSoldOut();
            }else {
                product.get().setUnSoldOut();
            }
            getProductDto(productRepository.saveAndFlush(product.get()));
        }
        throw new IllegalArgumentException();
    }

    public ProductDto setStockInfiniteState(Long productId, boolean isStockInfinite){
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            checkUserValidation(product.get().getSellerId());
            if (isStockInfinite){
                product.get().setStockInfinite();
            }else {
                product.get().setStockFinite();
            }
            getProductDto(productRepository.saveAndFlush(product.get()));
        }
        throw new IllegalArgumentException();
    }






    // manager 영역에서 사용할 것들



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
