package com.dinosoftlabs.dnews.Category.Category_Wise_News;

public class DataModel {
    String img_url;
    String title;
    int news_id;

    public DataModel(String img_url, String title, int news_id) {
        this.img_url = img_url;
        this.title = title;
        this.news_id = news_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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
}
