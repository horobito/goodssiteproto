package com.prototype.productmanager.controller;


import com.prototype.productmanager.service.ProductManagerService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController("/products")
@RequiredArgsConstructor
public class ProductManagerController {

    private final ProductManagerService productManagerService;

    @PostMapping
    public ModelAndView createProduct(@RequestBody ProductInfo productInfo){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("temp");
        mv.addObject(
                "productInfo",
                productManagerService.createProduct(
                        productInfo.getProductName(),
                        productInfo.getProductPrice(),
                        productInfo.getStock(),
                        productInfo.isStockInfinite(),
                        productInfo.getCategoryNames(),
                        productInfo.getImageUrl()
                ));
        return mv;

    }



    @DeleteMapping("/{productId}")
    public ModelAndView deleteProduct(@PathVariable Long productId){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("temp");
        mv.addObject(
                "productInfo",
                productManagerService.deleteProduct(productId
                ));
        return mv;
    }

    @PostMapping("/{productId}")
    public ModelAndView changeProductInfo(
            @RequestBody ProductInfo productInfo,
            @PathVariable Long productId){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("temp");
        mv.addObject(
                "productInfo",
                productManagerService.changeProductInfo(
                        productId,
                        productInfo.getProductName(),
                        productInfo.getProductPrice(),
                        productInfo.getStock(),
                        productInfo.isStockInfinite(),
                        productInfo.getCategoryNames(),
                        productInfo.getImageUrl()
                ));
        return mv;
    }



}

@Value
class ProductInfo{
    String productName;
    int productPrice;
    int stock;
    boolean isStockInfinite;
    List<String> categoryNames;
    String imageUrl;
}
