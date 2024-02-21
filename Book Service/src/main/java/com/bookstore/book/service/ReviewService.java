package com.bookstore.book.service;

import com.bookstore.book.model.Review;
import com.bookstore.book.repo.ReviewRepo;
import com.bookstore.commons.service.GenericService;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends GenericService<Review> {
    private final ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo) {
        super(reviewRepo);
        this.reviewRepo = reviewRepo;
    }
}
