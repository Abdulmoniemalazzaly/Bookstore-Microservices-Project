package com.bookstore.book.repo;

import com.bookstore.book.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category , Long> {
}
