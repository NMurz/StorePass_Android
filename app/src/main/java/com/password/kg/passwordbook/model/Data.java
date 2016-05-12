package com.password.kg.passwordbook.model;

import com.password.kg.model.Categories;

import java.util.List;

/**
 * Created by Nurs on 17.04.2016.
 */
public class Data {

    private List<Categories> categories;

    public Data(List<Categories> categories) {
        this.categories = categories;
    }

    public Data() {
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }
}
