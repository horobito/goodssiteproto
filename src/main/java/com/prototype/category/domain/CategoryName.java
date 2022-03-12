package com.prototype.category.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class CategoryName {

    private String categoryName;

    private CategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static CategoryName create(String categoryName){
        checkCategoryNameValidation(categoryName);
        return new CategoryName(categoryName);
    }

    private static void checkCategoryNameValidation(String categoryName){
        if (categoryName.length()==0 || categoryName.length()>10){
            throw new IllegalArgumentException();
        }
    }

    public String showValue(){
        return this.categoryName;
    }


}
