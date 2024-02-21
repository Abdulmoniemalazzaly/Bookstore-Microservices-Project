package com.bookstore.book.repo;

import com.bookstore.book.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher , Long> {
}
