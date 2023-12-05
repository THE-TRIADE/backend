package imd.ufrn.familyroutine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imd.ufrn.familyroutine.model.Category;
import imd.ufrn.familyroutine.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired 
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return this.categoryService.findAllCategories();
    }

    @GetMapping("{categoryId}")
    public Category findCategoryById(@PathVariable Long categoryId) {
        return this.categoryService.findCategoryById(categoryId);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category newCategory) {
        return this.categoryService.createCategory(newCategory);
    }

    @DeleteMapping
    public void deleteAllCategorys() {
        this.categoryService.deleteAllCategories();
    }

    @DeleteMapping("{categoryId}")
    public void deleteCategoryById(@PathVariable Long categoryId) {
        this.categoryService.deleteCategoryById(categoryId);
    }
    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody Category updateCategory) {
        return categoryService.updateCategory(categoryId, updateCategory);
    }
}