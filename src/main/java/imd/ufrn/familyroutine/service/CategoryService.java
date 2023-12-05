package imd.ufrn.familyroutine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imd.ufrn.familyroutine.model.Category;
import imd.ufrn.familyroutine.repository.CategoryRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category findCategoryById(Long categoryId) {
        return this.categoryRepository
            .findById(categoryId)
            .orElseThrow(
                () -> new EntityNotFoundException(categoryId, Category.class)
            );
    }
    public Category createCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public void deleteAllCategories() {
        this.categoryRepository.deleteAll();
    }

    public void deleteCategoryById(Long categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }

    public Category updateCategory(Long categoryId, Category putCategory) {
        if(!categoryRepository.existsById(categoryId)){
            throw new EntityNotFoundException(categoryId, Category.class);
        }

        putCategory.setId(categoryId);
        return this.categoryRepository.save(putCategory);
    }
}
