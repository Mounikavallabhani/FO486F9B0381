package com.example.hani.social_app.TopNews.DataModels;

public class Sliders_data_model {
    String image_url;
    String title;
    String descrition;
    int news_id;
    int like_dislike;

    public Sliders_data_model(String image_url,String title,String descrition,int news_id,int like_dislike) {
        this.image_url = image_url;
        this.descrition=descrition;
        this.title=title;
        this.news_id=news_id;
        this.like_dislike=like_dislike;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getLike_dislike() {
        return like_dislike;
    }

    public void setLike_dislike(int like_dislike) {
        this.like_dislike = like_dislike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
