package com.prototype.product.service;


import com.prototype.product.domain.*;
import com.prototype.user.service.UserDto;
import com.prototype.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final UserService userService;

    // manager 영역에서 사용할 것들

    public ProductDto create(String productName, int productPrice, int stock, boolean isStockInfinite, String imageUrl) {
        UserDto seller = userService.getLoggedInUser();
        Product newProduct = Product.create(
                ProductName.create(productName), ProductPrice.create(productPrice),
                SellerId.create(seller.getUserId()), Stock.create(stock, isStockInfinite), ImageUrl.create(imageUrl)
        );

        Product saved = productRepository.save(newProduct);

        return getProductDto(saved, seller.getUsername());
    }

    public ProductDto delete(Long productId) {
        ProductStrategy productDeleteStrategy = Product::delete;
        return doStrategy(productId, productDeleteStrategy);
    }

    public ProductDto revive(Long productId) {
        ProductStrategy productReviveStrategy = Product::revive;
        return doStrategy(productId, productReviveStrategy);
    }

    public ProductDto setSoldOutState(Long productId, boolean isSoldOut) {
        ProductStrategy productSetSoldOutStateStrategy = product -> {
            if (isSoldOut) {
                product.setSoldOut();
            } else {
                product.setUnSoldOut();
            }
        };
        return doStrategy(productId, productSetSoldOutStateStrategy);
    }

    public ProductDto update(Long productId, String productName, int productPrice, int amountOfStockChange, boolean isStockInfinite, String newImageUrl) {
        ProductStrategy updateStrategy = product -> {
            product.changeProductName(productName);
            product.changeProductPrice(productPrice);
            product.changeStock(amountOfStockChange);
            product.changeStockInfiniteState(isStockInfinite);
            product.changeImageUrl(newImageUrl);
        };
        return doStrategy(productId, updateStrategy);
    }

    public ProductDto deductStockAmount(Long productId,int deductedAmount) {
        ProductStrategy stockAmountDeductionStrategy = product -> product.deductStockAmount(deductedAmount);
        return doStrategy(productId,stockAmountDeductionStrategy );
    }

    public ProductDto doStrategy(Long productId, ProductStrategy productStrategy) {
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()) {
            UserDto loggedUser = userService.getLoggedInUser();
            checkUserValidation(loggedUser.getUserId(), product.get().getSellerId());
            productStrategy.executeStrategy(product.get());
            return getProductDto(
                    productRepository.save(product.get()),
                    loggedUser.getUsername());
        }
        throw new IllegalArgumentException();
    }


    private Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }


    private void checkUserValidation(Long loggedInUser, Long sellerId) {
        if (!loggedInUser.equals(sellerId)) {
            throw new IllegalArgumentException();
        }
    }


    private ProductDto getProductDto(Product product, String sellerName) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getSellerId(),
                sellerName,
                product.getStockAmount(),
                product.isStockInfinite(),
                product.isSoldOut(),
                product.isDeleted(),
                product.getImageUrl().showValue(),
                product.getRegistrationTime()
        );
    }


    public void checkProductExistence(Long id) {
        if (!productRepository.existsById(id)){
            throw new IllegalArgumentException();
        }
    }
}
