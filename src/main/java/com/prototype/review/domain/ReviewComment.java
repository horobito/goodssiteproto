package com.prototype.review.domain;


import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class ReviewComment {
    
    String reviewComment;

    private ReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }
    
    public static ReviewComment create(String reviewComment){
        checkReviewCommentValidation(reviewComment);
        return new ReviewComment(reviewComment);
    }

    private static void checkReviewCommentValidation(String reviewComment) {
        if (reviewComment.length()==0 || reviewComment.length()>1000){
            throw new IllegalArgumentException();
        }
    }

    public String showValue(){
        return this.reviewComment;
    }

}
