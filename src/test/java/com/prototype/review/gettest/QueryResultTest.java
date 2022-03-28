package com.prototype.review.gettest;


import com.prototype.review.domain.Review;
import com.prototype.review.domain.ReviewRepository;
import com.prototype.review.service.ReviewDto;
import com.prototype.review.service.ReviewService;
import com.prototype.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class QueryResultTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Mock
    UserService userService;

    @Test
    public void test1(){
        ReviewService sut  = new ReviewService(reviewRepository, userService);




        LocalDateTime time = LocalDateTime.parse(
                "2022-03-27 12:47:43.121", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        List<ReviewDto> result = sut.getProductReviews(
                1L, LocalDateTime.parse(
                        "2022-03-27 22:47:43.000", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                        ), 5
        );

        System.out.println(result.toString());
    }

    @Test
    public void test2(){
        ReviewService sut  = new ReviewService(reviewRepository, userService);




        List<Review> all = reviewRepository.findAll();


        System.out.println(all.toString());
    }

}
