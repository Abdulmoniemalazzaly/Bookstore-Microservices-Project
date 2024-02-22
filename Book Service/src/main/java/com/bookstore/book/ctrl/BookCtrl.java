package com.bookstore.book.ctrl;

import com.bookstore.book.model.Book;
import com.bookstore.book.service.BookService;
import com.bookstore.commons.ctrl.GenericCtrl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookCtrl extends GenericCtrl<Book> {

    private final BookService bookService;

    public BookCtrl(BookService bookService) {
        super(bookService);
        this.bookService = bookService;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Book>> findByCategory(@PathVariable(name = "id") Long categoryId){
        return ResponseEntity.ok(bookService.findByCategory(categoryId));
    }
}
