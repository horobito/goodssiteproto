package com.prototype.review.service;

import com.prototype.review.domain.*;
import com.prototype.user.service.UserDto;
import com.prototype.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;


    public ReviewDto create(Long orderedProductId,
                            String reviewComment, int score){
        UserDto reviewer = userService.getLoggedInUser();
        checkDuplication(orderedProductId, reviewer.getUserId());
        Review createdReview = Review.create(
                orderedProductId, reviewer.getUserId(), ReviewComment.create(reviewComment),
                ProductScore.create(score)
        );
        return getDto(reviewRepository.save(createdReview), reviewer.getUsername());
    }

    public ReviewDto delete(Long orderedProductId){
        ReviewStrategy deleteStrategy = Review::delete;
        return doReviewStrategy(orderedProductId, deleteStrategy);
    }

    public ReviewDto revive(Long orderedProductId){
        ReviewStrategy reviveStrategy = Review::revive;
        return doReviewStrategy(orderedProductId, reviveStrategy);
    }

    public ReviewDto changeReviewContent(Long orderedProductId, String newComment, int newScore ){
        ReviewStrategy reviewContentChangeStrategy = review -> review.changeReviewInfo(newComment, newScore);
        return doReviewStrategy(orderedProductId, reviewContentChangeStrategy);
    }


    public List<ReviewDto> getProductReviews(Long productId, Long cursor, int size){
        if (cursor==0){
            return convertToReviewDto(reviewRepository.findProductReview(productId, size));
        }else {
            return convertToReviewDto(reviewRepository.findProductReviews(productId, cursor, size));
        }
    }









    private List<ReviewDto> convertToReviewDto(List<Tuple> productReview) {
        return productReview.stream().map(
                it->new ReviewDto(
                        it.get(0, BigInteger.class).longValue(),
                        it.get(1, BigInteger.class).longValue(),
                        it.get(2, String.class),
                        it.get(3, Byte.class).intValue(),
                        it.get(4, Timestamp.class).toLocalDateTime(),
                        it.get(5, Boolean.class),
                        it.get(6, String.class)
                )
        ).collect(Collectors.toList());
    }


    private ReviewDto doReviewStrategy(Long orderedProductId, ReviewStrategy strategy) {
        UserDto loggedInUser = userService.getLoggedInUser();
        Optional<Review> review = reviewRepository.findById(ReviewId.create(orderedProductId, loggedInUser.getUserId()));
        if (review.isPresent()){
            checkUserValidation(loggedInUser.getUserId(), review.get().getReviewerId());
            strategy.executeStrategy(review.get());
            return getDto(reviewRepository.save(review.get()), loggedInUser.getUsername());
        }
        throw new IllegalArgumentException();
    }

    private void checkUserValidation(Long userId, Long reviewerId) {
        if (!userId.equals(reviewerId)){
            throw new IllegalArgumentException();
        }
    }

    private void checkDuplication(Long orderedProductId, Long userId) {
        if (reviewRepository.existsById(ReviewId.create(orderedProductId, userId))){
            throw new IllegalArgumentException();
        }
    }

    private ReviewDto getDto(Review review, String reviewerName) {
        return new ReviewDto(
                review.getOrderedProductId(),
                review.getReviewerId(),
                review.getReviewComment().showValue(),
                review.getProductScore().showValue(),
                review.getRegistrationDate(),
                review.isDeleted(),
                reviewerName
        );
    }
}
