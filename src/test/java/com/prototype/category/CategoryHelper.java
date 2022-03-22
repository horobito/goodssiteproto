package com.prototype.category;

import com.prototype.category.domain.Category;
import com.prototype.category.domain.CategoryName;

import javax.persistence.Embedded;

public class CategoryHelper extends Category {

    private Long categoryId;

    @Embedded
    private CategoryName categoryName;

    private boolean isDeleted;


    public CategoryHelper(CategoryName categoryName) {
        this.categoryName = categoryName;
        this.isDeleted = isDeleted;
    }

    public static CategoryHelper create(Long categoryId, CategoryName categoryName){
        CategoryHelper categoryHelper = new CategoryHelper(
                categoryName
        );

        categoryHelper.setCategoryId(categoryId);

        return categoryHelper;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
