package com.prototype.review.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class ReviewId implements Serializable {


    private Long orderedProductId;

    private Long reviewerId;

    private ReviewId(Long orderedProductId, Long reviewerId) {
        this.orderedProductId = orderedProductId;
        this.reviewerId = reviewerId;
    }

    public static ReviewId create(Long orderedProductId, Long reviewerId){
        return new ReviewId(orderedProductId, reviewerId);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            System.out.println("Object Same");
            return true;
        }

        if (!(obj instanceof ReviewId)) {
            return false;
        }


        ReviewId that = (ReviewId) obj;

        if (this.orderedProductId.equals(that.orderedProductId) && this.reviewerId.equals(that.reviewerId)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(orderedProductId, reviewerId);
    }
}
