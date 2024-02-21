package com.bookstore.book.repo;

import com.bookstore.book.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review , Long> {
}
