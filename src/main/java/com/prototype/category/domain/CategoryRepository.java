package com.prototype.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryNameIn(List<CategoryName> categoryNameList);

    Optional<Category> findByCategoryIdAndIsDeleted(Long categoryId, boolean b);
}






