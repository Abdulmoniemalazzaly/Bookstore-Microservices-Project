package com.bookstore.book.ctrl;

import com.bookstore.book.model.Review;
import com.bookstore.book.service.ReviewService;
import com.bookstore.commons.ctrl.GenericCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book/review")
public class ReviewCtrl extends GenericCtrl<Review> {
    private final ReviewService reviewService;

    public ReviewCtrl(ReviewService reviewService) {
        super(reviewService);
        this.reviewService = reviewService;
    }
}
