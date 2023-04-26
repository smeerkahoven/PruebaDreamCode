package com.dreamcode.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal de categorias.
 */
public class Category {

    // Nodo de subcategorias
    private HashMap<String, Category> subcategories = new HashMap<>();

    private Category root ;

    private String categoryName ;
    protected List<String> keywords = new ArrayList<>();

    public Category(final String categoryName, final Category parent, final List<String> keywords){
        this.keywords = keywords;
        this.categoryName = categoryName;
        this.root = parent;
    }

    public boolean addKeyword(final String categoryName, final String keyword){
        if (this.categoryName.equalsIgnoreCase(categoryName)){
            addKeyword(keyword);
            return true;
        }else {
            return addKeyword(categoryName, keyword, this);
        }
    }

    private void addKeyword(final String keyword){
        if (!this.keywords.contains(keyword)){
            this.keywords.add(keyword);
        }
    }

    public void addSubcategory (final String subcategory){
        this.subcategories.put(subcategory, new Category(subcategory,this, new ArrayList<>()));
    }
    public void addSubcategory(final String parentName, final String subcategory){

        if (this.categoryName.equalsIgnoreCase(parentName)){
            this.subcategories.put(subcategory, new Category(subcategory,this, new ArrayList<>()));
        }else {

            addSubcategory(parentName, subcategory, this);
        }
    }

    public boolean addKeyword(final String categoryName, final String keyword, final Category parentCategory){
        if (!parentCategory.subcategories.isEmpty()){
            for (final Map.Entry<String, Category> set : parentCategory.subcategories.entrySet()){
                if (set.getKey().equalsIgnoreCase(categoryName)) {
                    set.getValue().addKeyword(keyword);
                    return true;
                }else {
                    addKeyword(categoryName, keyword,set.getValue());
                }
            }
        }
        return false;
    }

    private void addSubcategory(final String parentName, final String subcategory, final Category parentCategory){

        if (!parentCategory.subcategories.isEmpty()){
            for (final Map.Entry<String, Category> set : parentCategory.subcategories.entrySet()){
                if (set.getKey().equalsIgnoreCase(parentName)) {
                    parentCategory.subcategories.put(subcategory, new Category(subcategory,parentCategory, new ArrayList<>()));
                }else {
                    addSubcategory(parentName, subcategory,set.getValue());
                }
            }
        }
    }


    public List<String> getKeyWords(final String category, final HashMap<String, Category> subcategories){

        List<String> result = new ArrayList() ;

        for (final Map.Entry<String, Category> set : subcategories.entrySet()){
            if (set.getKey().equalsIgnoreCase(category)) {
                return  set.getValue().getKeywords();
            } else {
                result = getKeyWords(category, set.getValue().getSubcategories());
            }
        }

        return result;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(final List<String> keywords) {
        this.keywords = keywords;
    }


    public HashMap<String, Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(final HashMap<String, Category> subcategories) {
        this.subcategories = subcategories;
    }

    public Category getRoot() {
        return root;
    }

    public void setRoot(final Category root) {
        this.root = root;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }
}
