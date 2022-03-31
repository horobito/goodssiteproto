package com.prototype.view.controller;


import com.prototype.view.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ViewService viewService;

    @GetMapping("/main")
    public String getMain(Model theModel){
        String today= LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        int size = 3;
        theModel.addAttribute("bestSeller", viewService.findOrderByScore(size));
        theModel.addAttribute("newProducts", viewService.findOrderByTime(today, size));


        return "main";
    }

    @GetMapping("/products/new")
    public String getNewProducts(Model theModel){
        String today= LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        int size = 10;
        theModel.addAttribute("bestSeller", viewService.findOrderByScore(size));
        theModel.addAttribute("newProducts", viewService.findOrderByTime(today, size));


        return "newproducts";
    }

    @GetMapping("/products/{productId}/reviews/{cursor}/{size}")
    public String getProductDetails(@PathVariable Long cursor, @PathVariable int size,
                                    @PathVariable Long productId, Model theModel){


        theModel.addAttribute("reviews", viewService.getReviews(productId, cursor, size));
        theModel.addAttribute("productDetails", viewService.getProductDetails(productId));

        return "productDetails";
    }
}
