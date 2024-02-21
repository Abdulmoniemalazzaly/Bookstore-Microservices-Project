package com.bookstore.book.repo;

import com.bookstore.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book , Long> {

    @Query("""
        SELECT B from Book B JOIN B.categories C WHERE C.id = :categoryId
    """)
    List<Book> findByCategory(@Param("categoryId") Long categoryId);
}
