package com.prototype.category.service;


import com.prototype.category.domain.Category;
import com.prototype.category.domain.CategoryName;
import com.prototype.category.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> createCategories(List<String> inputtedNames) {
        List<Category> existedCategories = categoryRepository.findByCategoryNameIn(getCategoryNames(inputtedNames));
        List<Category> newCategories = excludeDuplication(inputtedNames, existedCategories)
                .stream().map(Category::create).collect(Collectors.toList());
        List<Category> savedCategories = categoryRepository.saveAll(newCategories);

        return getCategoryDtos(joinCategories(existedCategories, savedCategories));
    }


    public void delete(Long categoryId){
        CategoryStrategy deleteStrategy = Category::delete;
        doStrategy(categoryId, deleteStrategy);
    }

    public void revive(Long categoryId){
        CategoryStrategy reviveStrategy = Category::revive;
        doStrategy(categoryId, reviveStrategy);
    }

    public void changeCategoryName(String newName, Long categoryId) {
        CategoryStrategy changeNameStrategy = category -> category.changeCategoryName(newName);
        doStrategy(categoryId, changeNameStrategy);
    }



    public void doStrategy(Long categoryId, CategoryStrategy strategy){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            strategy.doStrategy(category.get());
            categoryRepository.save(category.get());
        }else {
            throw new IllegalArgumentException();
        }
    }





    private List<CategoryDto> getCategoryDtos(List<Category> joinCategories) {
        return joinCategories.stream().map(this::getCategoryDto).collect(Collectors.toList());
    }


    private CategoryDto getCategoryDto(Category category) {
        return new CategoryDto(category.getCategoryId(), category.getCategoryName().showValue());
    }

    private List<Category> joinCategories(List<Category> existedCategories, List<Category> newCategories) {
        if (existedCategories.size() != 0 && newCategories.size() != 0) {
            return Stream.concat(existedCategories.stream(), newCategories.stream()).collect(Collectors.toList());
        } else if (existedCategories.size() == 0) {
            return newCategories;
        } else {
            return existedCategories;
        }
    }


    private List<CategoryName> excludeDuplication(List<String> inputtedNames, List<Category> existedCategories) {
        List<CategoryName> newCategoryNames = new ArrayList<>();

        Set<String> existedNames =
                existedCategories.stream().map(it -> it.getCategoryName().showValue()).collect(Collectors.toSet());

        inputtedNames.forEach(name -> {
            if (!existedNames.contains(name)) {
                newCategoryNames.add(CategoryName.create(name));
            }
        });
        return newCategoryNames;
    }


    private List<CategoryName> getCategoryNames(List<String> names) {
        return names.stream().map(CategoryName::create).collect(Collectors.toList());
    }

}
