package com.app.fixmykix.model;

import java.util.ArrayList;

public class DrawerItem {
    private String name;
    private ArrayList<ShopingCategory> categoryList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ShopingCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<ShopingCategory> categoryList) {
        this.categoryList = categoryList;
    }
}
