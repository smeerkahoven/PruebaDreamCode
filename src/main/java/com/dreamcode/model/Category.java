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

    /**
     * Adding keywords for each category
     * @param categoryName
     * @param keyword
     * @return
     */
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
            return 1;
        }else {
            if (!this.getSubcategories().isEmpty()) {
                return getLevelRecursive(categoryName, this, 2);
            }
        }
        return 0;
    }

    private Integer getLevelRecursive(final String categoryName, final Category parentCategory, final Integer level){

        Integer result = 0 ;
        if (!parentCategory.getSubcategories().isEmpty()){
            for (final Map.Entry<String, Category> set : parentCategory.subcategories.entrySet()){
                if (set.getKey().equalsIgnoreCase(categoryName)) {
                    return level;
                }else {
                    result = getLevelRecursive(categoryName, set.getValue(), level + 1)   ;
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
            this.subcategories.put(subcategory, new Category(subcategory,this, new ArrayList<>()));
            return true ;
        }else {
            addSubcategory(parentName, subcategory, this);
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

    /**
     * adding subcategories for each category according to its parent and category name
     * @param parentName
     * @param subcategory
     * @param parentCategory
     * @return
     */
    private boolean addSubcategory(final String parentName, final String subcategory, final Category parentCategory){

        if (!parentCategory.subcategories.isEmpty()){
            for (final Map.Entry<String, Category> set : parentCategory.subcategories.entrySet()){
                if (set.getKey().equalsIgnoreCase(parentName)) {
                    set.getValue().subcategories.put(subcategory, new Category(subcategory, set.getValue(), new ArrayList<>()));
                    return true ;
                }else {
                    addSubcategory(parentName, subcategory,set.getValue());
                }
            }
        }
        return false;
    }

    /**
     * Obtain keywords for each category if not found on child if will return for parent root
     * @param category
     * @param subcategories
     * @return
     */
    public List<String> getKeyWords(final String category, final HashMap<String, Category> subcategories){

        List<String> result = new ArrayList() ;

        for (final Map.Entry<String, Category> set : subcategories.entrySet()){
            if (set.getKey().equalsIgnoreCase(category)) {
                return getRootKeywords(set.getValue());
            } else {
                result = getKeyWords(category, set.getValue().getSubcategories());

                if(!result.isEmpty()){
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * Return list of keywords from parent if child does not have
     * @param rootCategory
     * @return
     */
    public List<String> getRootKeywords(final Category rootCategory){
        if ( rootCategory.getRoot() == null){
            return rootCategory.getKeywords();
        }else {
            final List<String> result = new ArrayList<>(rootCategory.getKeywords());
            result.addAll(getRootKeywords(rootCategory.getRoot()));

            return result;
        }
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

    public Category getRoot() {
        return root;
    }
}
