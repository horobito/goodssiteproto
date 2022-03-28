package com.prototype.review.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@IdClass(ReviewId.class)
@NoArgsConstructor
@Getter
public class Review {

    @Id
    Long orderedProductId;

    @Id
    Long reviewerId;

    @Embedded
    ReviewComment reviewComment;

    @Embedded
    ProductScore productScore;

    LocalDateTime registrationDate;

    boolean isDeleted;

    private Review(Long orderedProductId, Long reviewerId, ReviewComment reviewComment, ProductScore score) {
        this.orderedProductId = orderedProductId;
        this.reviewerId = reviewerId;
        this.reviewComment = reviewComment;
        this.productScore = score;
        this.isDeleted = false;
        this.registrationDate = LocalDateTime.now();
    }

    public static Review create(Long orderedProductId, Long reviewerId, ReviewComment reviewComment, ProductScore score){
        return new Review(orderedProductId, reviewerId, reviewComment, score);
    }


    public void delete(){
        if (this.isDeleted){
            throw new IllegalArgumentException();
        }
        this.isDeleted = true;
    }

    public void revive(){
        if (!this.isDeleted){
            throw new IllegalArgumentException();
        }
        this.isDeleted = false;
    }

    public void changeReviewInfo(String newComment, int newScore){
        this.reviewComment = ReviewComment.create(newComment);
        this.productScore = ProductScore.create(newScore);
        this.registrationDate = LocalDateTime.now();
    }




}
