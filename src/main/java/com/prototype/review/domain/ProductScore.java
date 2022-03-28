package com.prototype.review.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class ProductScore {

//    private final int MAX_SCORE = 10;

    int productScore;

    private ProductScore(int score) {
        this.productScore = score;
    }

    public static ProductScore create(int score){
        checkScoreValidation(score);
        return new ProductScore(score);
    }

    private static void checkScoreValidation(int score) {
        if (score<1 || score>10){
            throw new IllegalArgumentException();
        }
    }

    public int showValue(){
        return this.productScore;
    }



}
