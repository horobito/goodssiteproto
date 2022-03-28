package com.prototype.review.delete;


import com.prototype.review.domain.ProductScore;
import com.prototype.review.domain.Review;
import com.prototype.review.domain.ReviewComment;
import com.prototype.review.domain.ReviewRepository;
import com.prototype.review.service.ReviewService;
import com.prototype.user.service.UserDto;
import com.prototype.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    UserService userService;


    @DisplayName("Delete test 1. Normal Condition")
    @Test
    public void test1(){
        ReviewService sut = new ReviewService(reviewRepository, userService);

        Long loggedInUserId =1L;
        String loggedInUserName = "user";
        String loggedINUserPassword = "password";
        String loggedInUserGender = "MALE";

        Long productId = 1L;

        UserDto loggedInUser = new UserDto(
                loggedInUserId,
                loggedInUserName,
                loggedINUserPassword,
                loggedInUserGender,
                false,
                LocalDate.now());

        String comment = "comment";
        int maxScore = 10;
        int minScore = 1;

        Review review = Review.create(
                productId, loggedInUser.getUserId(), ReviewComment.create(comment), ProductScore.create(maxScore)
        );
        assertFalse( review.isDeleted());

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(reviewRepository.save(any())).thenReturn(review);

        sut.delete(productId);

        verify(reviewRepository, times(1)).save(any());

    }


    @DisplayName("Delete test 2. Abnormal Condition : reviewer mismatches user ")
    @Test
    public void test2(){
        ReviewService sut = new ReviewService(reviewRepository, userService);

        Long loggedInUserId =1L;
        String loggedInUserName = "user";
        String loggedINUserPassword = "password";
        String loggedInUserGender = "MALE";

        Long productId = 1L;

        UserDto loggedInUser = new UserDto(
                loggedInUserId,
                loggedInUserName,
                loggedINUserPassword,
                loggedInUserGender,
                false,
                LocalDate.now());

        Long reviewerId = 2L;

        String comment = "comment";
        int maxScore = 10;
        int minScore = 1;

        Review review = Review.create(
                productId, reviewerId, ReviewComment.create(comment), ProductScore.create(maxScore)
        );
        assertFalse( review.isDeleted());

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));

        assertThrows(IllegalArgumentException.class,
        ()->sut.delete(productId));

    }

    @DisplayName("Delete test 3. Abnormal Condition : already deleted ")
    @Test
    public void test3(){
        ReviewService sut = new ReviewService(reviewRepository, userService);

        Long loggedInUserId =1L;
        String loggedInUserName = "user";
        String loggedINUserPassword = "password";
        String loggedInUserGender = "MALE";

        Long productId = 1L;

        UserDto loggedInUser = new UserDto(
                loggedInUserId,
                loggedInUserName,
                loggedINUserPassword,
                loggedInUserGender,
                false,
                LocalDate.now());

        Long reviewerId = 2L;

        String comment = "comment";
        int maxScore = 10;
        int minScore = 1;

        Review review = Review.create(
                productId, reviewerId, ReviewComment.create(comment), ProductScore.create(maxScore)
        );
        review.delete();

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));

        assertThrows(IllegalArgumentException.class,
                ()->sut.delete(productId));

    }
}
