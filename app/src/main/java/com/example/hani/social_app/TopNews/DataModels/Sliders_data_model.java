package com.example.hani.social_app.TopNews.DataModels;

public class Sliders_data_model {
    String image_url;
    String title;
    String descrition;

    public Sliders_data_model(String image_url,String title,String descrition) {
        this.image_url = image_url;
        this.descrition=descrition;
        this.title=title;
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
