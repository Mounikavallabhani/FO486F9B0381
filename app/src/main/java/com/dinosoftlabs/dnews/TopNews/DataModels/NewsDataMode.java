package com.dinosoftlabs.dnews.TopNews.DataModels;

import org.json.JSONArray;

import java.util.ArrayList;

public class NewsDataMode {
    String title;
    String description;
    String image_url;
    String category;
    int id;
    int like_dislile;
    int is_section;
    JSONArray news_array;
    String Section_title;
    ArrayList<String> News_title_list, News_img_url;
    int news_section_id;
    public ArrayList<String> getNews_title_list() {
        return News_title_list;
    }

    public void setNews_title_list(ArrayList<String> news_title_list) {
        News_title_list = news_title_list;
    }

    public ArrayList<String> getNews_img_url() {
        return News_img_url;
    }

    public void setNews_img_url(ArrayList<String> news_img_url) {
        News_img_url = news_img_url;
    }

    public JSONArray getNews_array() {
        return news_array;
    }

    public void setNews_array(JSONArray news_array) {
        this.news_array = news_array;
    }

    public String getSection_title() {
        return Section_title;
    }

    public void setSection_title(String section_title) {
        Section_title = section_title;
    }

    public int getIs_section() {
        return is_section;
    }

    public void setIs_section(int is_section) {
        this.is_section = is_section;
    }

    public NewsDataMode(String imageUrl, String title, String description, String category, int id) {
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

    public NewsDataMode(String Section_title, ArrayList<String> News_title_list,ArrayList<String> News_imag_url) {
        this.Section_title = Section_title;
        this.News_title_list = News_title_list;
        this.News_img_url= News_imag_url;
    }

    public NewsDataMode(String Section_title, JSONArray array, int News_Section_id) {
        this.Section_title = Section_title;
        this.news_array= array;
        this.news_section_id = News_Section_id;
    }

    public int getNews_section_id() {
        return news_section_id;
    }

    public void setNews_section_id(int news_section_id) {
        this.news_section_id = news_section_id;
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
