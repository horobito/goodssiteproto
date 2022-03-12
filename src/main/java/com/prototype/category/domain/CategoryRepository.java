package com.prototype.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryNameIn(List<CategoryName> categoryNameList);

    Optional<Category> findByCategoryIdAndDeleted(Long categoryId, boolean b);
}






