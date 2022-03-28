package com.prototype.review.revive;

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
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class ReviveTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    UserService userService;


    @DisplayName("Revive test 1. Normal Condition")
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
        int score = 10;

        Review review = Review.create(
                productId, loggedInUser.getUserId(), ReviewComment.create(comment), ProductScore.create(score)
        );
        review.delete();
        assertTrue( review.isDeleted());

        when(userService.getLoggedInUser()).thenReturn(loggedInUser);
        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(reviewRepository.save(any())).thenReturn(review);

        sut.revive(productId);

        verify(reviewRepository, times(1)).save(any());

    }



}
