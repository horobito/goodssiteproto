package com.prototype.productmanager.service;


import com.prototype.category.service.CategoryDto;
import com.prototype.category.service.CategoryService;
import com.prototype.category_product_relation.service.CategoryProductRelationService;
import com.prototype.product.service.ProductDto;
import com.prototype.product.service.ProductService;
import com.prototype.productmanager.domain.EssentialProductInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductManagerService {

    private final ProductService productService;

    private final CategoryService  categoryService;

    private final CategoryProductRelationService relationService;

    private final EssentialProductInfoRepository essentialProductInfoRepository;

    @Transactional
    public EssentialProductInfo createProduct(
            String productName, int productPrice,
            int stock, boolean isStockInfinite, List<String> categoryNames, String imageUrl){

        ProductDto productDto = productService.create(productName, productPrice, stock, isStockInfinite, imageUrl);
        List<CategoryDto> categoryDtos = categoryService.createCategories(categoryNames);
        relationService.createRelations(
                categoryDtos.stream().map(it->it.getCategoryId()).collect(Collectors.toList()),
                productDto.getProductId());
        return getEssentialProductDto(productDto, categoryDtos);
    }

    @Transactional
    public EssentialProductInfo deleteProduct(
            Long productId){
        ProductDto deletedProduct = productService.delete(productId);
        List<CategoryDto> relatedCategory = categoryService.getRelatedCategory(productId);
        return getEssentialProductDto(deletedProduct, relatedCategory);
    }

    public List<EssentialProductInfo> get(Long productId){
        return essentialProductInfoRepository.findByProductId(productId, EssentialProductInfo.class);
    }


    @Transactional
    public EssentialProductInfo changeProductInfo(
            Long productId,  String productName, int productPrice,
            int amountOfChange, boolean isStockInfinite,List<String> categoryNames, String imageUrl){
        ProductDto updatedProduct = productService.update(productId, productName, productPrice, amountOfChange, isStockInfinite, imageUrl);
        List<CategoryDto> newCategories = categoryService.createCategories(categoryNames);
        relationService.update(productId, newCategories.stream().map(CategoryDto::getCategoryId).collect(Collectors.toList()));
        return getEssentialProductDto(updatedProduct, newCategories);
    }



    private EssentialProductInfo getEssentialProductDto(ProductDto productDto, List<CategoryDto> categoryDtos) {
        return new EssentialProductInfo(
                productDto.getProductId(),
                productDto.getProductName(),
                productDto.getSellerId(),
                productDto.getSellerName(),
                productDto.getProductPrice(),
                productDto.isSoldOut(),
                categoryDtos,
                productDto.getImageUrl()
        );
    }

}
