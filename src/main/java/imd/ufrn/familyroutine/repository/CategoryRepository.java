package imd.ufrn.familyroutine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
