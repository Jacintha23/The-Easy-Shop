package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories()
    {
        var categories = categoryRepository.findAll();

        return categories;
    }

    public Category getById(int categoryId)
    {
        var category = categoryRepository.getById(categoryId);                // get category by id
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public Category create(Category category)
    {
        //var newCategory =                                             // create a new category
        return categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category)
    {
        category.setCategoryId(categoryId);
        return categoryRepository.save(category);              // update category and return the updated category
    }

    public void delete(int categoryId)
    {
        categoryRepository.deleteById(categoryId);                // delete category
    }


}
