package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

@RestController //create a rest controller
@RequestMapping("/categories") /*add the annotation to make this controller the endpoint for the following url
                                http://localhost:8080/categories*/
@CrossOrigin //add annotation to allow cross site origin requests

public class CategoriesController
{
    private CategoryService categoryService;
    private ProductService productService;

    // create an Autowired constructor to inject the categoryService and productService
    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService)
    {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("")                                // add the appropriate annotation for a get action
    public ResponseEntity<List<Category>> getAll()
    // find and return all categories
    {
        var categories = categoryService.findAllCategories();

        return ResponseEntity.ok(categories);
    }


    @GetMapping("{id}")      // add the appropriate annotation for a get action
    public ResponseEntity<Category> getById(@PathVariable int id)
    {
        var category =  categoryService.getById(id); // get the category by id
        if(category == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(category);

    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsById(@PathVariable int categoryId)
    {
        var products = productService.listByCategoryId(categoryId);  // get a list of product by categoryId, refer to Product Service
        return ResponseEntity.ok(products);
    }

    @PostMapping ("")                                           // add annotation to call this method for a POST action
    @PreAuthorize(("hasRole('ROLE_ADMIN')"))                    // add annotation to ensure that only an ADMIN can call this function
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        var newCategory = categoryService.create(category);     // insert the category,
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory); //  and return it with status 201 Created
    }

    @PutMapping("{id}")                                    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    @PreAuthorize(("hasRole('ROLE_ADMIN')"))                  // add annotation to ensure that only an ADMIN can call this function
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        Category updatedCategory = categoryService.update(id, category);   // update the category by id,
        return ResponseEntity.ok(updatedCategory);         // and return the updated category (200 OK)
    }


    @DeleteMapping({"id"})                         // add annotation to call this method for a DELETE action - the url path must include the categoryId
    @PreAuthorize(("hasRole('ROLE_ADMIN')"))                                             // add annotation to ensure that only an ADMIN can call this function
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        categoryService.delete(id);                       // delete the category by id and return status 204 No Content
        return ResponseEntity.noContent().build();            //NO_CONTENT
    }
}
