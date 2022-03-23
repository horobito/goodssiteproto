package com.prototype.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryNameIn(List<CategoryName> categoryNameList);

    @Query(value = "select c.*\n" +
            "from categoryd c\n" +
            "    left join category_product_relation cpr on cpr.product_id=:productId and cpr.is_deleted=false\n" +
            "where c.category_id = cpr.category_id and c.is_deleted = false", nativeQuery = true)
    List<Category> findByRelatedCategory(Long productId);

}






