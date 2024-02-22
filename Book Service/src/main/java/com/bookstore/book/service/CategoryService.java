package com.bookstore.book.service;

import com.bookstore.book.model.Category;
import com.bookstore.book.repo.CategoryRepo;
import com.bookstore.commons.service.GenericService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService extends GenericService<Category> {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        super(categoryRepo);
        this.categoryRepo = categoryRepo;
    }
}
