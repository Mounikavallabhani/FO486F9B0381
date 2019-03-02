package com.example.hani.social_app.Category.CategoryModel;

public class CategoryModel {
    String name;
    String image_url;
    int cate_id;

    public CategoryModel(String name, String image_url, int cate_id) {
        this.name = name;
        this.image_url = image_url;
        this.cate_id = cate_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }
}
