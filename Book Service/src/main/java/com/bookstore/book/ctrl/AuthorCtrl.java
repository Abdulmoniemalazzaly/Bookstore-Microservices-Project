package com.bookstore.book.ctrl;

import com.bookstore.book.model.Author;
import com.bookstore.book.service.AuthorService;
import com.bookstore.commons.ctrl.GenericCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book/author")
public class AuthorCtrl extends GenericCtrl<Author> {
    private final AuthorService authorService;

    public AuthorCtrl(AuthorService authorService) {
        super(authorService);
        this.authorService = authorService;
    }
}
