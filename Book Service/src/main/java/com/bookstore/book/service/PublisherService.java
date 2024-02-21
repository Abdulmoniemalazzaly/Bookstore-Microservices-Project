package com.bookstore.book.service;

import com.bookstore.book.model.Publisher;
import com.bookstore.book.repo.PublisherRepo;
import com.bookstore.commons.service.GenericService;
import org.springframework.stereotype.Service;

@Service
public class PublisherService extends GenericService<Publisher> {

    private final PublisherRepo publisherRepo;

    public PublisherService(PublisherRepo publisherRepo) {
        super(publisherRepo);
        this.publisherRepo = publisherRepo;
    }
}
