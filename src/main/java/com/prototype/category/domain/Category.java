package com.prototype.category.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Embedded
    private CategoryName categoryName;

    private boolean isDeleted;

    private Category(CategoryName categoryName) {
        this.categoryName = categoryName;
        this.isDeleted = false;
    }

    public static Category create(CategoryName categoryName) {
        return new Category(categoryName);
    }


    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void changeCategoryName(String newCategoryName) {
        this.categoryName = CategoryName.create(newCategoryName);
    }

    public void delete() {
        if (this.isDeleted) {
            throw new IllegalArgumentException();
        }
        this.isDeleted = true;
    }

    public void revive() {
        if (!this.isDeleted) {
            throw new IllegalArgumentException();
        }
        this.isDeleted = false;
    }

}
