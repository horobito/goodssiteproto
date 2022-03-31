package com.prototype.view.service;


import com.prototype.category.service.CategoryDto;
import com.prototype.product.service.ProductService;
import com.prototype.review.service.ReviewDto;
import com.prototype.review.service.ReviewService;
import com.prototype.view.domain.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewRepository viewRepository;

    private final ProductService productService;

    private final ReviewService reviewService;


    public List<ProductInfoDto> findOrderByScore(int size){
        return convertToViewDto(viewRepository.findOrderByScore(size));
    }

    public List<ProductInfoDto> findOrderByTime(String cursor, int size){
        LocalDateTime time = LocalDateTime.parse(
                cursor, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        return convertToViewDto(viewRepository.findOrderByRegistTime(time, size));
    }


    private List<ProductInfoDto> convertToViewDto(List<Tuple> products) {
        return products.stream().map(
                product->new ProductInfoDto(
                        product.get(0, BigInteger.class).longValue(),
                        product.get(1,String.class),
                        product.get(2, BigInteger.class).longValue(),
                        product.get(3, String.class),
                        product.get(4, BigDecimal.class).doubleValue(),
                        product.get(5, BigInteger.class).intValue(),
                        product.get(6, Integer.class),
                        product.get(7, Boolean.class),
                        product.get(8, Boolean.class),
                        product.get(9,Boolean.class),
                        convertToCategories(product.get(10, String.class)),
                        product.get(11, Integer.class),
                        product.get(12, String.class),
                        product.get(13, Timestamp.class).toLocalDateTime()
                        )

        ).collect(Collectors.toList());
    }

    private List<CategoryDto> convertToCategories(String categoryInfos) {
        String[] categories = categoryInfos.split("&");
        if (categories[0].equals("")){
            return Collections.EMPTY_LIST;
        }

        return Arrays.stream(categories).map(
                it->{
                    String[] info = it.split("#");
                    return new CategoryDto(
                            Long.parseLong(info[0]),
                            info[1]
                    );
                }
        ).collect(Collectors.toList());
    }


    public ProductInfoDto getProductDetails(Long id) {
        productService.checkProductExistence(id);
        return convertToViewDto(viewRepository.findByProductId(id)).get(0);
    }

    public List<ReviewDto> getReviews(Long productId, Long cursor, int size) {
            return reviewService.getProductReviews(productId, cursor, size);
    }
}
