package com.bookstore.book.repo;

import com.bookstore.book.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepo extends JpaRepository<Author, Long> {
}
