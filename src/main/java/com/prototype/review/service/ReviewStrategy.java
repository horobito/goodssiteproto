package com.prototype.review.service;

import com.prototype.review.domain.Review;

public interface ReviewStrategy {
    public void executeStrategy(Review review);
}
