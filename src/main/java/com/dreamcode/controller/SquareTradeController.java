package com.dreamcode.controller;

import com.dreamcode.model.Category;

import java.util.ArrayList;
import java.util.List;

public class SquareTradeController {

    private final List<Category> categories = new ArrayList<>();

    public void createCategory(final String categoryName, final Category parent, final List<String> keywords) {
        if (!categories.contains(categoryName)) {
            categories.add(new Category(categoryName, parent, keywords, 1));
        }
    }

    public void createCategory(final String categoryName) {
        createCategory(categoryName, null, new ArrayList<>());
    }

    public void createSubcategory(final String parentCategory, final String subcategory) {
        for (final Category category : categories) {
            if (category.addSubcategory(parentCategory, subcategory)) {
                break;
            }
        }
    }

    public void addKeyword(final String keyword, final String category) {
        for (final Category item : categories) {
            if (item.addKeyword(keyword, category)) {
                break;
            }
        }
    }

    public Integer displayLevel(final String category){
        for (final Category item : categories) {
            final Integer result = item.getLevel(category);
            if (result != 0) {
                return result ;
            }
        }

        return -1;
    }

    public List<String> getKeyWords(final String category) {

        for (final Category item : categories) {

            if (item.getCategoryName().equalsIgnoreCase(category)) {
                return item.getKeywords();
            } else if (!item.getSubcategories().isEmpty()) {

                return item.getKeyWords(category, item.getSubcategories());
            }
        }

        return new ArrayList<>();

    }

    public List<Category> getCategories() {
        return categories;
    }

}
