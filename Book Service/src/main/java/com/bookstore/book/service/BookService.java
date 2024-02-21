package com.bookstore.book.service;

import com.bookstore.book.model.Book;
import com.bookstore.book.model.Review;
import com.bookstore.book.repo.BookRepo;
import com.bookstore.commons.service.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends GenericService<Book> {

    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        super(bookRepo);
        this.bookRepo = bookRepo;
    }

    public List<Book> findByCategory(Long categoryId) {
        return bookRepo.findByCategory(categoryId);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = super.findAll();
        CalculateRatings(books);
        return books;
    }

    private void CalculateRatings(List<Book> books){
        Float sum = 0F;
        for (Book b : books){
            sum += b.getReviews().stream()
                    .map(Review::getRating)
                    .reduce((r1, r2) -> Float.sum(r1, r2)).get();
            b.setRating(sum / b.getReviews().size());
            sum = 0F;
        }
    }
}
