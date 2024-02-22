package com.bookstore.book.service;

import com.bookstore.book.model.Author;
import com.bookstore.book.repo.AuthorRepo;
import com.bookstore.commons.service.GenericService;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends GenericService<Author> {

    private final AuthorRepo authorRepo;

    public AuthorService(AuthorRepo authorRepo) {
        super(authorRepo);
        this.authorRepo = authorRepo;
    }
}
