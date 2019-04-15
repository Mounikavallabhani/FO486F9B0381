package com.dinosoftlabs.dnews.TopNews.DataModels;

public class News_Section_Model {
    String image_url;
    String title;
    int news_id;
    int is_like_dis_like;
    String ago_time;

    public String getAgo_time() {
        return ago_time;
    }

    public void setAgo_time(String ago_time) {
        this.ago_time = ago_time;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getIs_like_dis_like() {
        return is_like_dis_like;
    }

    public void setIs_like_dis_like(int is_like_dis_like) {
        this.is_like_dis_like = is_like_dis_like;
    }

    public News_Section_Model(String image_url, String title, int news_id, int is_like_dis_like, String ago_time) {
        this.image_url = image_url;
        this.title = title;
        this.news_id = news_id;
        this.is_like_dis_like = is_like_dis_like;
        this.ago_time = ago_time;
    }
}
