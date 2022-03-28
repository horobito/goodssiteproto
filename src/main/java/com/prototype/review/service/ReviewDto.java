package com.prototype.review.service;


import lombok.Setter;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Setter
public class ReviewDto{


    Long orderedProductId;


    Long reviewerId;

    String reviewComment;

    int score;

    LocalDateTime registrationDate;

    boolean isDeleted;

    String reviewerName;
}
