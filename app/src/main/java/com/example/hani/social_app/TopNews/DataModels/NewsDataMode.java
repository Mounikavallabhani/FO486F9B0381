package com.example.hani.social_app.TopNews.DataModels;

public class NewsDataMode {
    String title;
    String description;
    String image_url;
    String category;
    int id;
    int like_dislile;
    public NewsDataMode(String imageUrl, String title, String description,String category,int id) {
        this.image_url = imageUrl;
        this.title = title;
        this.description = description;
        this.category=category;
        this.id=id;
    }

    public NewsDataMode(String imageUrl, String title, String description,String category,int id,int like_dislile) {
        this.image_url = imageUrl;
        this.title = title;
        this.description = description;
        this.category=category;
        this.id=id;
        this.like_dislile=like_dislile;
    }

    public int getLike_dislile() {
        return like_dislile;
    }

    public void setLike_dislile(int like_dislile) {
        this.like_dislile = like_dislile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
