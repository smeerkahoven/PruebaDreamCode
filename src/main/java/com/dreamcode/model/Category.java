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

    private Integer level;

    private String categoryName ;
    protected List<String> keywords = new ArrayList<>();

    public Category(final String categoryName, final Category parent, final List<String> keywords, final Integer level){
        this.keywords = keywords;
        this.categoryName = categoryName;
        this.root = parent;
        this.level = level;
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

    public Integer getLevel(final String categoryName){
        if(this.categoryName.equalsIgnoreCase(categoryName)){
            return getLevel();
        }else {
            if (!this.getSubcategories().isEmpty()) {
                return getLevelRecursive(categoryName, this);
            }
        }
        return 0;
    }

    private Integer getLevelRecursive(final String categoryName, final Category parentCategory){

        Integer result = 0 ;
        if (!parentCategory.getSubcategories().isEmpty()){
            for (final Map.Entry<String, Category> set : parentCategory.subcategories.entrySet()){
                if (set.getKey().equalsIgnoreCase(categoryName)) {
                    return set.getValue().getLevel();
                }else {
                    result = getLevelRecursive(categoryName, set.getValue())   ;
                }

                if (result != 0){
                    return result ;
                }
            }
        }
        return result;
    }

    public boolean addSubcategory(final String parentName, final String subcategory){

        if (this.categoryName.equalsIgnoreCase(parentName)){
            this.subcategories.put(subcategory, new Category(subcategory,this, new ArrayList<>(),level + 1));
            return true ;
        }else {
            addSubcategory(parentName, subcategory, this, level + 1);
        }

        return false;
    }

    private boolean addKeyword(final String categoryName, final String keyword, final Category parentCategory){
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

    private boolean addSubcategory(final String parentName, final String subcategory, final Category parentCategory, final Integer levelp){

        if (!parentCategory.subcategories.isEmpty()){
            for (final Map.Entry<String, Category> set : parentCategory.subcategories.entrySet()){
                if (set.getKey().equalsIgnoreCase(parentName)) {
                    set.getValue().subcategories.put(subcategory, new Category(subcategory,parentCategory, new ArrayList<>(), levelp + 1 ));
                    return true ;
                }else {
                    addSubcategory(parentName, subcategory,set.getValue(), levelp + 1);
                }
            }
        }
        return false;
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

    public HashMap<String, Category> getSubcategories() {
        return subcategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getLevel() {
        return level;
    }

}
