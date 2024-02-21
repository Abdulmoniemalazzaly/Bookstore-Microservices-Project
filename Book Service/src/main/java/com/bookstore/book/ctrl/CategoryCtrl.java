package com.bookstore.book.ctrl;

import com.bookstore.book.model.Category;
import com.bookstore.book.service.CategoryService;
import com.bookstore.commons.ctrl.GenericCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book/category")
public class CategoryCtrl extends GenericCtrl<Category> {

    private final CategoryService categoryService;

    public CategoryCtrl(CategoryService categoryService) {
        super(categoryService);
        this.categoryService = categoryService;
    }
}
